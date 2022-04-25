package com.group2.campus.campus.client;


import com.group2.nustudy.vo.camp.ScheduleOrderVo;
import com.group2.nustudy.vo.order.SignInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-campus")
@Repository
public interface CampusFeignClient {

    /**
     * 根据排班id获取预约下单数据
     */
    @GetMapping("/api/campus/campus/inner/getScheduleOrderVo/{scheduleId}")
    ScheduleOrderVo getScheduleOrderVo(@PathVariable("scheduleId") String scheduleId);

    /**
     * 获取医院签名信息
     */
    @GetMapping("/api/campus/campus/inner/getSignInfoVo/{hoscode}")
    public SignInfoVo getSignInfoVo(@PathVariable("hoscode") String hoscode);
}
