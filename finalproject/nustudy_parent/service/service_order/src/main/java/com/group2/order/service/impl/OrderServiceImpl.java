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
        //获取就诊人信息
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
            //预约记录唯一标识（医院预约记录主键）
            String hosRecordId = jsonObject.getString("hosRecordId");
            //预约序号
            Integer number = jsonObject.getInteger("number");
            ;
            //取号时间
            String fetchTime = jsonObject.getString("fetchTime");
            ;
            //取号地址
            String fetchAddress = jsonObject.getString("fetchAddress");
            ;
            //更新订单
            orderInfo.setCampusRecordId(hosRecordId);
            orderInfo.setNumber(number);
            orderInfo.setFetchTime(fetchTime);
            orderInfo.setFetchAddress(fetchAddress);
            baseMapper.updateById(orderInfo);
            //排班可预约数
            Integer reservedNumber = jsonObject.getInteger("reservedNumber");
            //排班剩余预约数
            Integer availableNumber = jsonObject.getInteger("availableNumber");
            //发送mq消息，号源更新和短信通知
            //发送mq信息更新号源
            OrderMqVo orderMqVo = new OrderMqVo();
            orderMqVo.setScheduleId(scheduleId);
            orderMqVo.setReservedNumber(reservedNumber);
            orderMqVo.setAvailableNumber(availableNumber);
            //短信提示
//            MsmVo msmVo = new MsmVo();
//            msmVo.setPhone(orderInfo.getPatientPhone());
//            String reserveDate = new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd") + (orderInfo.getReserveTime()==0 ? "上午" : "下午");
//            Map<String,Object> param = new HashMap<String,Object>(){{
//                put("title", orderInfo.getHosname()+"|"+orderInfo.getDepname()+"|"+orderInfo.getTitle());
//                put("amount", orderInfo.getAmount());
//                put("reserveDate", reserveDate);
//                put("name", orderInfo.getPatientName());
//                put("quitTime", new DateTime(orderInfo.getQuitTime()).toString("yyyy-MM-dd HH:mm"));
//            }};
//            msmVo.setParam(param);
//            orderMqVo.setMsmVo(msmVo);

            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, orderMqVo);
        } else {
            throw new NustudyException(result.getString("message"), ResultCodeEnum.FAIL.getCode());
        }
        return orderInfo.getId();
    }

    //根据订单id查询订单详情
    @Override
    public OrderInfo getOrder(String orderId) {
        OrderInfo orderInfo = baseMapper.selectById(orderId);
        return orderInfo;
    }

    //订单列表（条件查询带分页）
//    @Override
//    public IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo) {
//        //orderQueryVo获取条件值
//        String name = orderQueryVo.getKeyword(); //医院名称
//        Long patientId = orderQueryVo.getPatientId(); //就诊人名称
//        String orderStatus = orderQueryVo.getOrderStatus(); //订单状态
//        String reserveDate = orderQueryVo.getReserveDate();//安排时间
//        String createTimeBegin = orderQueryVo.getCreateTimeBegin();
//        String createTimeEnd = orderQueryVo.getCreateTimeEnd();
//
//        //对条件值进行非空判断
//        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
//        if(!StringUtils.isEmpty(name)) {
//            wrapper.like("hosname",name);
//        }
//        if(!StringUtils.isEmpty(patientId)) {
//            wrapper.eq("patient_id",patientId);
//        }
//        if(!StringUtils.isEmpty(orderStatus)) {
//            wrapper.eq("order_status",orderStatus);
//        }
//        if(!StringUtils.isEmpty(reserveDate)) {
//            wrapper.ge("reserve_date",reserveDate);
//        }
//        if(!StringUtils.isEmpty(createTimeBegin)) {
//            wrapper.ge("create_time",createTimeBegin);
//        }
//        if(!StringUtils.isEmpty(createTimeEnd)) {
//            wrapper.le("create_time",createTimeEnd);
//        }
//        //调用mapper的方法
//        IPage<OrderInfo> pages = baseMapper.selectPage(pageParam, wrapper);
//        //编号变成对应值封装
//        pages.getRecords().stream().forEach(item -> {
//            this.packOrderInfo(item);
//        });
//        return pages;
//    }

    //取消预约
//    @Override
//    public Boolean cancelOrder(Long orderId) {
//        //获取订单信息
//        OrderInfo orderInfo = baseMapper.selectById(orderId);
//        //判断是否取消
//        DateTime quitTime = new DateTime(orderInfo.getQuitTime());
//        if(quitTime.isBeforeNow()) {
//            throw new NustudyException(ResultCodeEnum.CANCEL_ORDER_NO);
//        }
//        //调用医院接口实现预约取消
//        SignInfoVo signInfoVo = campusFeignClient.getSignInfoVo(orderInfo.getHoscode());
////        if(null == signInfoVo) {
////            throw new NustudyException(ResultCodeEnum.PARAM_ERROR);
////        }
//        Map<String, Object> reqMap = new HashMap<>();
//        reqMap.put("hoscode",orderInfo.getHoscode());
//        reqMap.put("hosRecordId",orderInfo.getHosRecordId());
//        reqMap.put("timestamp", HttpRequestHelper.getTimestamp());
//        String sign = HttpRequestHelper.getSign(reqMap, signInfoVo.getSignKey());
//        reqMap.put("sign", sign);
//
//        JSONObject result = HttpRequestHelper.sendRequest(reqMap,
//                signInfoVo.getApiUrl()+"/order/updateCancelStatus");
//        //根据医院接口返回数据
//        if(result.getInteger("code")!=200) {
//            throw new NustudyException(result.getString("message"), ResultCodeEnum.FAIL.getCode());
//        } else {
//            //判断当前订单是否可以取消
////            if(orderInfo.getOrderStatus().intValue() == OrderStatusEnum.PAID.getStatus().intValue()) {
////                Boolean isRefund = weixinService.refund(orderId);
////                if(!isRefund) {
////                    throw new YyghException(ResultCodeEnum.CANCEL_ORDER_FAIL);
////                }
////                //更新订单状态
////                orderInfo.setOrderStatus(OrderStatusEnum.CANCLE.getStatus());
////                baseMapper.updateById(orderInfo);
////
////                //发送mq更新预约数量
////                OrderMqVo orderMqVo = new OrderMqVo();
////                orderMqVo.setScheduleId(orderInfo.getScheduleId());
////                //短信提示
////                MsmVo msmVo = new MsmVo();
////                msmVo.setPhone(orderInfo.getPatientPhone());
////                String reserveDate = new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd") + (orderInfo.getReserveTime()==0 ? "上午": "下午");
////                Map<String,Object> param = new HashMap<String,Object>(){{
////                    put("title", orderInfo.getHosname()+"|"+orderInfo.getDepname()+"|"+orderInfo.getTitle());
////                    put("reserveDate", reserveDate);
////                    put("name", orderInfo.getPatientName());
////                }};
////                msmVo.setParam(param);
////                orderMqVo.setMsmVo(msmVo);
////                rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_ORDER, MqConst.ROUTING_ORDER, orderMqVo);
////            }
//        }
//        return true;
//    }

    //就诊通知
//    @Override
//    public void patientTips() {
////        QueryWrapper<OrderInfo> wrapper = new QueryWrapper<>();
////        wrapper.eq("reserve_date",new DateTime().toString("yyyy-MM-dd"));
////        wrapper.ne("order_status",OrderStatusEnum.CANCLE.getStatus());
////        List<OrderInfo> orderInfoList = baseMapper.selectList(wrapper);
////        for(OrderInfo orderInfo:orderInfoList) {
////            //短信提示
////            MsmVo msmVo = new MsmVo();
////            msmVo.setPhone(orderInfo.getPatientPhone());
////            String reserveDate = new DateTime(orderInfo.getReserveDate()).toString("yyyy-MM-dd") + (orderInfo.getReserveTime()==0 ? "上午": "下午");
////            Map<String,Object> param = new HashMap<String,Object>(){{
////                put("title", orderInfo.getHosname()+"|"+orderInfo.getDepname()+"|"+orderInfo.getTitle());
////                put("reserveDate", reserveDate);
////                put("name", orderInfo.getPatientName());
////            }};
////            msmVo.setParam(param);
////            rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_MSM, MqConst.ROUTING_MSM_ITEM, msmVo);
////        }
//    }

    //预约统计
//    @Override
//    public Map<String, Object> getCountMap(OrderCountQueryVo orderCountQueryVo) {
//        //调用mapper方法得到数据
//        List<OrderCountVo> orderCountVoList = baseMapper.selectOrderCount(orderCountQueryVo);
//
//        //获取x需要数据 ，日期数据  list集合
//        List<String> dateList = orderCountVoList.stream().map(OrderCountVo::getReserveDate).collect(Collectors.toList());
//
//        //获取y需要数据，具体数量  list集合
//        List<Integer> countList =orderCountVoList.stream().map(OrderCountVo::getCount).collect(Collectors.toList());
//
//        Map<String,Object> map = new HashMap<>();
//        map.put("dateList",dateList);
//        map.put("countList",countList);
//        return map;
//        return null;
//    }

    private OrderInfo packOrderInfo(OrderInfo orderInfo) {
        orderInfo.getParam().put("orderStatusString", OrderStatusEnum.getStatusNameByStatus(orderInfo.getOrderStatus()));
        System.out.println("orderInfo: " + orderInfo.toString());
        return orderInfo;
    }

}
