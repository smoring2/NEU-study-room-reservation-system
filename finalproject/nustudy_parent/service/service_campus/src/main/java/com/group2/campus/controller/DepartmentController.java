package com.group2.campus.controller;

import com.group2.campus.service.DepartmentService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.vo.camp.DepartmentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/campus/department")
//@CrossOrigin
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    //According to the campus number, query the list of all departments in the campus
    @ApiOperation(value = "query the list of all departments in the campus")
    @GetMapping("getDeptList/{campuscode}")
    public Result getDeptList(@PathVariable String campuscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(campuscode);
        return Result.ok(list);
    }
}
