package com.group2.campus.controller.api;

import com.group2.campus.service.CampusService;
import com.group2.campus.service.CampusSetService;
import com.group2.campus.service.DepartmentService;
import com.group2.campus.service.ScheduleService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.model.camp.Schedule;
import com.group2.nustudy.vo.camp.CampusQueryVo;
import com.group2.nustudy.vo.camp.DepartmentVo;
import com.group2.nustudy.vo.camp.ScheduleOrderVo;
import com.group2.nustudy.vo.order.SignInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/campus/campus")
public class CampusApiController {
    @Autowired
    private CampusService campusService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CampusSetService campusSetService;

    @Operation(summary = "findCampusList")
    @GetMapping(value = "findCampusList/{page}/{limit}")
    public Result findCampusList(@PathVariable Integer page,
                                 @PathVariable Integer limit,
                                 CampusQueryVo campusQueryVo) {
        Page<Campus> campuses = campusService.selectCampusPage(page, limit, campusQueryVo);
//         just for testing
//        List<Campus> content = campuses.getContent();
//        campuses.getTotalPages();
        return Result.ok(campuses);
    }

    @Operation(summary = "findByCampusName")
    @GetMapping(value = "findByCampusName/{campusName}")
    public Result findByCampusName(@PathVariable String campusName){
        List<Campus> list = campusService.findByCampusName(campusName);
        return Result.ok(list);
    }

    @Operation(summary = "findDepartmentByCampuscode")
    @GetMapping("department/{campusCode}")
    public Result index(@PathVariable String campusCode) {
        List<DepartmentVo> list = departmentService.findDeptTree(campusCode);
        return Result.ok(list);
    }

    @Operation(summary = "findCampusDetailByCampuscode")
    @GetMapping("findCampusDetail/{campuscode}")
    public Result item(@PathVariable String campuscode) {
        Map<String, Object> map = campusService.item(campuscode);
        return Result.ok(map);
    }

    @Operation(summary = "getBookingSchedule")
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{campuscode}/{depcode}")
    public Result getBookingSchedule(
            @Parameter(name = "page", description = "current page", required = true)
            @PathVariable Integer page,
            @Parameter(name = "limit", description = "records per page", required = true)
            @PathVariable Integer limit,
            @Parameter(name = "campuscode", description = "campuscode", required = true)
            @PathVariable String campuscode,
            @Parameter(name = "depcode", description = "depcode", required = true)
            @PathVariable String depcode) {
        return Result.ok(scheduleService.getBookingScheduleRule(page, limit, campuscode, depcode));
    }

    @Operation(summary = "findScheduleList")
    @GetMapping("auth/findScheduleList/{campuscode}/{depcode}/{workDate}")
    public Result findScheduleList(
            @Parameter(name = "campuscode", description = "campuscode", required = true)
            @PathVariable String campuscode,
            @Parameter(name = "depcode", description = "depcode", required = true)
            @PathVariable String depcode,
            @Parameter(name = "workDate", description = "workDate", required = true)
            @PathVariable String workDate) {
        return Result.ok(scheduleService.getDetailSchedule(campuscode, depcode, workDate));
    }

    @Operation(summary = "getScheduleByScheduleId")
    @GetMapping("getSchedule/{scheduleId}")
    public Result getSchedule(@PathVariable String scheduleId) {
        Schedule schedule = scheduleService.getScheduleId(scheduleId);
        return Result.ok(schedule);
    }

    @Operation(summary = "getScheduleOrderVo")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @Parameter(name = "scheduleId", description = "schaduleId", required = true)
            @PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @Operation(summary = "getSignInfoVo")
    @GetMapping("inner/getSignInfoVo/{campuscode}")
    public SignInfoVo getSignInfoVo(
            @Parameter(name = "campuscode", description = "campuscode", required = true)
            @PathVariable("campuscode") String campuscode) {
        return campusSetService.getSignInfoVo(campuscode);
    }


}
