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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "findCampusList")
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

    @ApiOperation(value = "findByCampusName")
    @GetMapping(value = "findByCampusName/{campusName}")
    public Result findByCampusName(@PathVariable String campusName){
        List<Campus> list = campusService.findByCampusName(campusName);
        return Result.ok(list);
    }

    @ApiOperation(value = "findDepartmentByCampuscode")
    @GetMapping("department/{campusCode}")
    public Result index(@PathVariable String campusCode) {
        List<DepartmentVo> list = departmentService.findDeptTree(campusCode);
        return Result.ok(list);
    }

    @ApiOperation(value = "findCampusDetailByCampuscode")
    @GetMapping("findCampusDetail/{campuscode}")
    public Result item(@PathVariable String campuscode) {
        Map<String, Object> map = campusService.item(campuscode);
        return Result.ok(map);
    }

    @ApiOperation(value = "getBookingSchedule")
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{campuscode}/{depcode}")
    public Result getBookingSchedule(
            @ApiParam(name = "page", value = "current page", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "records per page", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "campuscode", value = "campuscode", required = true)
            @PathVariable String campuscode,
            @ApiParam(name = "depcode", value = "depcode", required = true)
            @PathVariable String depcode) {
        return Result.ok(scheduleService.getBookingScheduleRule(page, limit, campuscode, depcode));
    }

    @ApiOperation(value = "findScheduleList")
    @GetMapping("auth/findScheduleList/{campuscode}/{depcode}/{workDate}")
    public Result findScheduleList(
            @ApiParam(name = "campuscode", value = "campuscode", required = true)
            @PathVariable String campuscode,
            @ApiParam(name = "depcode", value = "depcode", required = true)
            @PathVariable String depcode,
            @ApiParam(name = "workDate", value = "workDate", required = true)
            @PathVariable String workDate) {
        return Result.ok(scheduleService.getDetailSchedule(campuscode, depcode, workDate));
    }

    @ApiOperation(value = "getScheduleByScheduleId")
    @GetMapping("getSchedule/{scheduleId}")
    public Result getSchedule(@PathVariable String scheduleId) {
        Schedule schedule = scheduleService.getScheduleId(scheduleId);
        return Result.ok(schedule);
    }

    @ApiOperation(value = "getScheduleOrderVo")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @ApiParam(name = "scheduleId", value = "schaduleId", required = true)
            @PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @ApiOperation(value = "getSignInfoVo")
    @GetMapping("inner/getSignInfoVo/{campuscode}")
    public SignInfoVo getSignInfoVo(
            @ApiParam(name = "campuscode", value = "campuscode", required = true)
            @PathVariable("campuscode") String campuscode) {
        return campusSetService.getSignInfoVo(campuscode);
    }


}
