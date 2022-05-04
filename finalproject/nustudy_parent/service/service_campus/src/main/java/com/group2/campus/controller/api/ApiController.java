package com.group2.campus.controller.api;

import com.group2.campus.service.CampusService;
import com.group2.campus.service.DepartmentService;
import com.group2.campus.service.CampusSetService;
import com.group2.campus.service.ScheduleService;
import com.group2.nustudy.common.exception.NustudyException;
import com.group2.nustudy.common.helper.HttpRequestHelper;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.common.result.ResultCodeEnum;
import com.group2.nustudy.common.utils.MD5;
import com.group2.nustudy.model.camp.Department;
import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.model.camp.Schedule;
import com.group2.nustudy.vo.camp.DepartmentQueryVo;
import com.group2.nustudy.vo.camp.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/campus")
public class ApiController {

    @Autowired
    private CampusService campusService;

    @Autowired
    private CampusSetService campusSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    // delete schedule api
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request) {
        //Get the information passed over to the department
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //Get campus number and shift number
        String campuscode = (String)paramMap.get("campuscode");
        String hosScheduleId = (String)paramMap.get("hosScheduleId");

        //TODO sign_key check

        scheduleService.remove(campuscode,hosScheduleId);
        return Result.ok();
    }

    // lookup schedule api
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        //Get the information passed over to the department
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        // campus code
        String campuscode = (String)paramMap.get("campuscode");

        //department code
        String depcode = (String)paramMap.get("depcode");
        //current page and records per page
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO sign_key check

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setCampuscode(campuscode);
        scheduleQueryVo.setDepcode(depcode);
        //use service method
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }

    //Upload schedule interface
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        //Get the information passed over to the department
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //TODO sign_key check
        scheduleService.save(paramMap);
        return Result.ok();
    }

    //Delete department interface
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        //Get the information passed over to the department
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //Campus number and department number
        String campuscode = (String)paramMap.get("campuscode");
        String depcode = (String)paramMap.get("depcode");
        //TODO sign_key check
        departmentService.remove(campuscode,depcode);
        return Result.ok();
    }

    //Query department interface
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        // get department info
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        // campus code
        String campuscode = (String)paramMap.get("campuscode");
        // current page and the limitation of each page
        int page = StringUtils.isEmpty(paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit")) ? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //TODO sign_key check

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setCampuscode(campuscode);

        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }

    //Upload department interface
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        //Get the information passed over to the department
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //Get campus number
        String campuscode = (String)paramMap.get("campuscode");
        //1 Obtain the signature passed by the campus system, and perform MD5 encryption on the signature
        String hospSign = (String)paramMap.get("sign");

        //2 According to the passed campus code, query the database and query the signature
        String signKey = campusSetService.getSignKey(campuscode);

        //3 MD5 encryption of database query signature
        String signKeyMd5 = MD5.encrypt(signKey);

        //4 Check whether the signature is consistent
        if(!hospSign.equals(signKeyMd5)) {
            throw new NustudyException(ResultCodeEnum.SIGN_ERROR);
        }

        //use service method
        departmentService.save(paramMap);
        return Result.ok();
    }

//    Inquire about the campus
    @PostMapping("campus/show")
    public Result getCampus(HttpServletRequest request) {
        //Get the information passed over to the campus
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //Get campus number
        String campuscode = (String)paramMap.get("campuscode");
        //1 Obtain the signature passed by the campus system, and perform MD5 encryption on the signature
        String hospSign = (String)paramMap.get("sign");

        //2 According to the passed campus code, query the database and query the signature
        String signKey = campusSetService.getSignKey(campuscode);

        //3 MD5 encryption of database query signature
        String signKeyMd5 = MD5.encrypt(signKey);

        //4 Check whether the signature is consistent
        if(!hospSign.equals(signKeyMd5)) {
            throw new NustudyException(ResultCodeEnum.SIGN_ERROR);
        }

        //Call the service method to query according to the campus number
        Campus campus = campusService.getByCampuscode(campuscode);
        return Result.ok(campus);
    }

    //Upload campus interface
    @PostMapping("saveCampus")
    public Result saveCamp(HttpServletRequest request) {
        //Get the information passed over to the campus
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //1 get key and encode with MD5
        String hospSign = (String)paramMap.get("sign");

        //2 lookup the sign via campus code
        String campuscode = (String)paramMap.get("campuscode");
        String signKey = campusSetService.getSignKey(campuscode);

        //3 MD5 encode
        String signKeyMd5 = MD5.encrypt(signKey);

        //4 check key
        if(!hospSign.equals(signKeyMd5)) {
            throw new NustudyException(ResultCodeEnum.SIGN_ERROR);
        }

        // fix bug - in the transmission, '+' is converted to ' '
        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);

        //use service method
        campusService.save(paramMap);
        return Result.ok();
    }




}
