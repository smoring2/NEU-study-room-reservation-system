package com.group2.campus.controller;

import com.group2.campus.service.CampusService;
import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.vo.camp.CampusQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/campus/campus")
@CrossOrigin
public class CampusController {
    @Autowired
    private CampusService campusService;

    // campus list

    @GetMapping("list/{page}/{limit}")
    public Result listCampus(@PathVariable Integer page,
                             @PathVariable Integer limit,
                             CampusQueryVo campusQueryVo) {
        Page<Campus> pageModel = campusService.selectCampusPage(page, limit, campusQueryVo);
        return Result.ok(pageModel);
    }
}
