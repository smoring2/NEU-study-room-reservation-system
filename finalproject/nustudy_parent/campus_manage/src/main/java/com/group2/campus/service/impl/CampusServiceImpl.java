package com.group2.campus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.group2.campus.mapper.OrderInfoMapper;
import com.group2.campus.mapper.ScheduleMapper;
import com.group2.campus.model.OrderInfo;
import com.group2.campus.model.Schedule;
import com.group2.campus.service.CampusService;
import com.group2.campus.util.ResultCodeEnum;
import com.group2.campus.util.NustudyException;
import com.group2.nustudy.model.user.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CampusServiceImpl implements CampusService {

	@Autowired
	private ScheduleMapper scheduleMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> submitOrder(Map<String, Object> paramMap) {
        log.info(JSONObject.toJSONString(paramMap));
        String campuscode = (String)paramMap.get("campuscode");
        String depcode = (String)paramMap.get("depcode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
        String reserveDate = (String)paramMap.get("reserveDate");
        String reserveTime = (String)paramMap.get("reserveTime");
        String amount = (String)paramMap.get("amount");

        Schedule schedule = this.getSchedule(hosScheduleId);
        if(null == schedule) {
            throw new NustudyException(ResultCodeEnum.DATA_ERROR);
        }

        if(!schedule.getCampuscode().equals(campuscode)
                || !schedule.getDepcode().equals(depcode)
                || !schedule.getAmount().toString().equals(amount)) {
            throw new NustudyException(ResultCodeEnum.DATA_ERROR);
        }
        System.out.println(JSONObject.toJSONString(paramMap));

        //student info
        Student student = JSONObject.parseObject(JSONObject.toJSONString(paramMap), Student.class);
        System.out.println("student: " + student);
        log.info(JSONObject.toJSONString(student));
        //deal with student reservation
        Long studentId = this.saveStudent(student);

        Map<String, Object> resultMap = new HashMap<>();
        int availableNumber = schedule.getAvailableNumber().intValue();
        if(availableNumber > 0) {
            schedule.setAvailableNumber(availableNumber - 1);
            scheduleMapper.updateById(schedule);

            //record reservation
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setStudentId(studentId);
            orderInfo.setScheduleId(Long.parseLong(hosScheduleId));
            int number = schedule.getReservedNumber().intValue() - schedule.getAvailableNumber().intValue();
            orderInfo.setNumber(number);
            orderInfo.setAmount(new BigDecimal(amount));
            String fetchTime = "0".equals(reserveDate) ? " 09:30 before" : " 14:00 before";
            orderInfo.setFetchTime(reserveTime + fetchTime);
            orderInfo.setFetchAddress("4 N. 2nd Street San Jose, CA 95113");
            //default unordered
            orderInfo.setOrderStatus(0);
            orderInfoMapper.insert(orderInfo);

            resultMap.put("resultCode","0000");
            resultMap.put("resultMsg","success");
            //only signal of reservation
            resultMap.put("hosRecordId", orderInfo.getId());
            //reserved order
            resultMap.put("number", number);
            //reserved time
            resultMap.put("fetchTime", reserveDate + "09:00 before");;
            //room address
            resultMap.put("fetchAddress", "4 N. 2nd Street San Jose, CA 95113");;
            //reserved room number
            resultMap.put("reservedNumber", schedule.getReservedNumber());
            //room available
            resultMap.put("availableNumber", schedule.getAvailableNumber());
        } else {
            throw new NustudyException(ResultCodeEnum.DATA_ERROR);
        }
        return resultMap;
    }

    @Override
    public void updatePayStatus(Map<String, Object> paramMap) {
        String campuscode = (String)paramMap.get("campuscode");
        String hosRecordId = (String)paramMap.get("hosRecordId");

        OrderInfo orderInfo = orderInfoMapper.selectById(hosRecordId);
        if(null == orderInfo) {
            throw new NustudyException(ResultCodeEnum.DATA_ERROR);
        }
        //ordered
        orderInfo.setOrderStatus(1);
        orderInfo.setPayTime(new Date());
        orderInfoMapper.updateById(orderInfo);
    }

    @Override
    public void updateCancelStatus(Map<String, Object> paramMap) {
        String campuscode = (String)paramMap.get("campuscode");
        String hosRecordId = (String)paramMap.get("hosRecordId");

        OrderInfo orderInfo = orderInfoMapper.selectById(hosRecordId);
        if(null == orderInfo) {
            throw new NustudyException(ResultCodeEnum.DATA_ERROR);
        }
        //canceled
        orderInfo.setOrderStatus(-1);
        orderInfo.setQuitTime(new Date());
        orderInfoMapper.updateById(orderInfo);
    }

    private Schedule getSchedule(String frontSchId) {
        System.out.println("frontID: " + frontSchId);
        return scheduleMapper.selectById(frontSchId);
    }

    /**
     * record student info
     * @param student
     */
    private Long saveStudent(Student student) {

        return 1L;
    }


}
