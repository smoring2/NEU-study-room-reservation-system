package com.group2.campus.controller.api;

import com.group2.campus.service.CampusService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.vo.camp.CampusQueryVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/campus/campus")
public class CampusApiController {
    @Autowired
    private CampusService campusService;

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

}
