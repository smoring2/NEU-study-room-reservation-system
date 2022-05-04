package com.group2.order.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.campus.campus.client.CampusFeignClient;
import com.group2.campus.user.client.StudentFeignClient;
import com.group2.common.rabbit.constant.MqConst;
import com.group2.common.rabbit.service.RabbitService;
import com.group2.nustudy.common.exception.NustudyException;
import com.group2.nustudy.common.helper.HttpRequestHelper;
import com.group2.nustudy.common.result.ResultCodeEnum;
import com.group2.nustudy.enums.OrderStatusEnum;
import com.group2.nustudy.model.order.OrderInfo;
import com.group2.nustudy.model.user.Student;
import com.group2.nustudy.vo.camp.ScheduleOrderVo;
import com.group2.nustudy.vo.order.*;
import com.group2.order.mapper.OrderMapper;
import com.group2.order.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderServiceImpl extends
        ServiceImpl<OrderMapper, OrderInfo> implements OrderService {

    @Autowired
    private CampusFeignClient campusFeignClient;

    @Autowired
    private StudentFeignClient studentFeignClient;

    @Autowired
    private RabbitService rabbitService;

    //Generate orders
    @Override
    public Long saveOrder(String scheduleId, Long studentId) {
        //student info
        Student student = studentFeignClient.getStudentOrder(studentId);
        System.out.println("student: " + student);

        //Get information about scheduling
        ScheduleOrderVo scheduleOrderVo = campusFeignClient.getScheduleOrderVo(scheduleId);

        //Determine if the current time is still available for appointment
        if (new DateTime(scheduleOrderVo.getStartTime()).isAfterNow()
                || new DateTime(scheduleOrderVo.getEndTime()).isBeforeNow()) {
            throw new NustudyException(ResultCodeEnum.TIME_NO);
        }

        //Get signature information
        SignInfoVo signInfoVo = campusFeignClient.getSignInfoVo(scheduleOrderVo.getCampuscode());

        //add to order form
        OrderInfo orderInfo = new OrderInfo();
        //scheduleOrderVo to orderInfo
        BeanUtils.copyProperties(scheduleOrderVo, orderInfo);
        //set the data of orderInfo
        String outTradeNo = System.currentTimeMillis() + "" + new Random().nextInt(100);
        orderInfo.setOutTradeNo(outTradeNo);
        orderInfo.setScheduleId(scheduleId);
        orderInfo.setUserId(student.getUserId());
        orderInfo.setStudentId(studentId);
        orderInfo.setStudentName(student.getName());
        orderInfo.setStudentEmail(student.getEmail());
        orderInfo.setOrderStatus(OrderStatusEnum.UNPAID.getStatus());
        baseMapper.insert(orderInfo);

        //Call the campus interface to realize the appointment registration operation
        //Set the parameters required to call the campus interface, and put the parameters in the map collection
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("campuscode", orderInfo.getCampuscode());
        paramMap.put("depcode", orderInfo.getDepcode());
        paramMap.put("hosScheduleId", orderInfo.getScheduleId());
        paramMap.put("reserveDate", new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd"));
        paramMap.put("reserveTime", orderInfo.getReserveTime());
        paramMap.put("amount", orderInfo.getAmount());

        paramMap.put("name", student.getName());
        paramMap.put("certificatesType", student.getCertificatesType());
        paramMap.put("certificatesNo", student.getCertificatesNo());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        paramMap.put("email", student.getEmail());
        paramMap.put("stateCode", student.getStateCode());
        paramMap.put("cityCode", student.getCityCode());
        paramMap.put("districtCode", student.getDistrictCode());
        paramMap.put("address", student.getAddress());
        paramMap.put("timestamp", HttpRequestHelper.getTimestamp());

        String sign = HttpRequestHelper.getSign(paramMap, signInfoVo.getSignKey());
        paramMap.put("sign", sign);
        System.out.println("paraMap: " + paramMap);
        //require campus interface
        JSONObject result = HttpRequestHelper.sendRequest(paramMap, signInfoVo.getApiUrl() + "/order/submitOrder");

        if (result.getInteger("code") == 200) {
            JSONObject jsonObject = result.getJSONObject("data");
            //Appointment record unique identifier (campus appointment record primary key)
            String hosRecordId = jsonObject.getString("hosRecordId");
            //reservation number
            Integer number = jsonObject.getInteger("number");
            ;
            //reservation time
            String fetchTime = jsonObject.getString("fetchTime");
            ;
            //fetch address
            String fetchAddress = jsonObject.getString("fetchAddress");
            ;
            //Update order
            orderInfo.setCampusRecordId(hosRecordId);
            orderInfo.setNumber(number);
            orderInfo.setFetchTime(fetchTime);
            orderInfo.setFetchAddress(fetchAddress);
            baseMapper.updateById(orderInfo);
            //Scheduled number of appointments
            Integer reservedNumber = jsonObject.getInteger("reservedNumber");
            //The number of remaining appointments in the schedule
            Integer availableNumber = jsonObject.getInteger("availableNumber");
            //Send mq message, number source update and SMS notification
            //Send mq information to update number source
            OrderMqVo orderMqVo = new OrderMqVo();
            orderMqVo.setScheduleId(scheduleId);
            orderMqVo.setReservedNumber(reservedNumber);
            orderMqVo.setAvailableNumber(availableNumber);


            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, orderMqVo);
        } else {
            throw new NustudyException(result.getString("message"), ResultCodeEnum.FAIL.getCode());
        }
        return orderInfo.getId();
    }

    //Query order details based on order id
    @Override
    public OrderInfo getOrder(String orderId) {
        OrderInfo orderInfo = baseMapper.selectById(orderId);
        return orderInfo;
    }



    private OrderInfo packOrderInfo(OrderInfo orderInfo) {
        orderInfo.getParam().put("orderStatusString", OrderStatusEnum.getStatusNameByStatus(orderInfo.getOrderStatus()));
        System.out.println("orderInfo: " + orderInfo.toString());
        return orderInfo;
    }

}
