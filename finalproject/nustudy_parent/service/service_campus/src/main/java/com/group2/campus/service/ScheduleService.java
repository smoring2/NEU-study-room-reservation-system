package com.group2.campus.service;

import com.group2.nustudy.model.camp.Schedule;
import com.group2.nustudy.vo.camp.ScheduleOrderVo;
import com.group2.nustudy.vo.camp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ScheduleService {
    void save(Map<String, Object> paramMap);

    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);

    void remove(String campuscode, String hosScheduleId);

    Map<String, Object> getRuleSchedule(long page, long limit, String campuscode, String depcode);

    List<Schedule> getDetailSchedule(String campuscode, String depcode, String workDate);

    Map<String,Object> getBookingScheduleRule(int page,int limit,String campuscode,String depcode);

    Schedule getScheduleId(String scheduleId);

    ScheduleOrderVo getScheduleOrderVo(String scheduleId);

    void update(Schedule schedule);
}
