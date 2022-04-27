package com.group2.campus.controller;

import com.group2.campus.service.ScheduleService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.camp.Schedule;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/campus/schedule")
//@CrossOrigin
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    // according to the campuscode & departmentcode
    @ApiOperation(value = "Look up schedule rulers")
    @GetMapping(value = "getScheduleRule/{page}/{limit}/{campuscode}/{depcode}")
    public Result getScheduleRule(@PathVariable long page,
                                  @PathVariable long limit,
                                  @PathVariable String campuscode,
                                  @PathVariable String depcode) {
        Map<String, Object> map = scheduleService.getRuleSchedule(page, limit, campuscode, depcode);
        return Result.ok(map);
    }

    @ApiOperation(value = "get schedule details")
    @GetMapping(value = "getScheduleDetail/{campuscode}/{depcode}/{workDate}")
    public Result getScheduleDetail(@PathVariable String campuscode,
                                    @PathVariable String depcode,
                                    @PathVariable String workDate){
        List<Schedule> list = scheduleService.getDetailSchedule(campuscode, depcode, workDate);
        return Result.ok(list);
    }
}
