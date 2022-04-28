package com.group2.campus.controller;

import com.group2.campus.service.CampusService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.vo.camp.CampusQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/campus/campus")
//@CrossOrigin
public class CampusController {
    @Autowired
    private CampusService campusService;

    // campus list
    @GetMapping("list/{page}/{limit}")
    public Result listCampus(@PathVariable Integer page,
                             @PathVariable Integer limit,
                             CampusQueryVo campusQueryVo) {
        Page<Campus> pageModel = campusService.selectCampusPage(page, limit, campusQueryVo);
        List<Campus> content = pageModel.getContent();
        long totalElements = pageModel.getTotalElements();
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "updateCampusStatus")
    @GetMapping("updateCampusStatus/{id}/{status}")
    public Result updateCampusStatus(@PathVariable String id, @PathVariable Integer status) {
        campusService.updateStatus(id,status);
        return Result.ok();
    }

    @ApiOperation(value = "Campus Info")
    @GetMapping("showCampusDetail/{id}")
    public Result showCampusDetail(@PathVariable String id) {
        Map<String, Object> map = campusService.getCampusById(id);
        return Result.ok(map);
    }

}
