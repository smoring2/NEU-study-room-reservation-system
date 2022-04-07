package com.group2.campus.controller;

import com.atguigu.yygh.model.hosp.Hospital;
import com.atguigu.yygh.model.hosp.HospitalSet;
import com.group2.campus.service.HospitalSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */

@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    //read all data
    // http://localhost:8201/admin/hosp/hospitalSet/findAll
    @GetMapping("findAll")
    public List<HospitalSet> findAllHospitalSet(){
        List<HospitalSet> list = hospitalSetService.list();
        return list;
    }

}
