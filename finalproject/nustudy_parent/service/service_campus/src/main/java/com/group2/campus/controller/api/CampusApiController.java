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
//         just for tutorial
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

    @ApiOperation(value = "根据医院编号获取科室")
    @GetMapping("department/{campusCode}")
    public Result index(@PathVariable String campusCode) {
        List<DepartmentVo> list = departmentService.findDeptTree(campusCode);
        return Result.ok(list);
    }

    @ApiOperation(value = "根据医院编号获取医院预约挂号详情")
    @GetMapping("findCampusDetail/{campuscode}")
    public Result item(@PathVariable String campuscode) {
        Map<String, Object> map = campusService.item(campuscode);
        return Result.ok(map);
    }

    @ApiOperation(value = "获取可预约排班数据")
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{campuscode}/{depcode}")
    public Result getBookingSchedule(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "campuscode", value = "医院code", required = true)
            @PathVariable String campuscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode) {
        return Result.ok(scheduleService.getBookingScheduleRule(page, limit, campuscode, depcode));
    }

    @ApiOperation(value = "获取排班数据")
    @GetMapping("auth/findScheduleList/{campuscode}/{depcode}/{workDate}")
    public Result findScheduleList(
            @ApiParam(name = "campuscode", value = "医院code", required = true)
            @PathVariable String campuscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode,
            @ApiParam(name = "workDate", value = "排班日期", required = true)
            @PathVariable String workDate) {
        return Result.ok(scheduleService.getDetailSchedule(campuscode, depcode, workDate));
    }

    @ApiOperation(value = "获取排班id获取排班数据")
    @GetMapping("getSchedule/{scheduleId}")
    public Result getSchedule(@PathVariable String scheduleId) {
        Schedule schedule = scheduleService.getScheduleId(scheduleId);
        return Result.ok(schedule);
    }

    @ApiOperation(value = "根据排班id获取预约下单数据")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @ApiOperation(value = "获取医院签名信息")
    @GetMapping("inner/getSignInfoVo/{campuscode}")
    public SignInfoVo getSignInfoVo(
            @ApiParam(name = "campuscode", value = "医院code", required = true)
            @PathVariable("campuscode") String campuscode) {
        return campusSetService.getSignInfoVo(campuscode);
    }


}
