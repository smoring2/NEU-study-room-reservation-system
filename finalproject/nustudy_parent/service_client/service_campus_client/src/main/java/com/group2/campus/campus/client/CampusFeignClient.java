package com.group2.campus.campus.client;


import com.group2.nustudy.vo.camp.ScheduleOrderVo;
import com.group2.nustudy.vo.order.SignInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient of service-campus
 */
@FeignClient(value = "service-campus")
@Repository
public interface CampusFeignClient {

    /**
     * getScheduleOrderVo by scheduleId
     */
    @GetMapping("/api/campus/campus/inner/getScheduleOrderVo/{scheduleId}")
    ScheduleOrderVo getScheduleOrderVo(@PathVariable("scheduleId") String scheduleId);

    /**
     * get campus information by its code
     */
    @GetMapping("/api/campus/campus/inner/getSignInfoVo/{campuscode}")
    public SignInfoVo getSignInfoVo(@PathVariable("campuscode") String campuscode);
}
