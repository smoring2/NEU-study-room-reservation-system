package com.group2.campus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.group2.campus.cmn.client.DictFeignClient;
import com.group2.campus.repository.CampusRepository;
import com.group2.campus.service.CampusService;
import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.vo.camp.CampusQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@Service
public class CampusServiceImpl implements CampusService {

    @Autowired
    private CampusRepository campusRepository;

    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public void save(Map<String, Object> paramMap) {
        String mapString = JSONObject.toJSONString(paramMap);
        Campus campus = JSONObject.parseObject(mapString, Campus.class);

        // check whether the data exist
        String campuscode = campus.getCampuscode();
        Campus campusExist = campusRepository.getCampusByCampuscode(campuscode);

        // modify if exists
        if (campusExist != null) {
            campus.setStatus(campusExist.getStatus());
            campus.setCreateTime(campusExist.getCreateTime());
            campus.setUpdateTime(new Date());
            campus.setIsDeleted(0);
            campusRepository.save(campus);
        } else {// add
            campus.setStatus(0);
            campus.setCreateTime(new Date());
            campus.setUpdateTime(new Date());
            campus.setIsDeleted(0);
            campusRepository.save(campus);
        }
    }

    @Override
    public Campus getByCampuscode(String campuscode) {
        Campus campus = campusRepository.getCampusByCampuscode(campuscode);
        return campus;
    }

    // list
    @Override
    public Page<Campus> selectCampusPage(Integer page, Integer limit, CampusQueryVo campusQueryVo) {
        //create pageable object
        Pageable pageable = PageRequest.of(page - 1, limit);
        //Create a condition matcher
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        //campusQueryVo to Campus
        Campus campus = new Campus();
        BeanUtils.copyProperties(campusQueryVo, campus);

        Example<Campus> example = Example.of(campus, matcher);
        Page<Campus> pages = campusRepository.findAll(pageable);

        System.out.println("Campus: " + pages.getSize() + ", " + pages.getTotalElements());
        pages.getContent().stream().forEach(item -> {
            System.out.println("item: " + item);
            this.setCampusCamType(item);
        });

        return pages;
    }

    //
    // update the status of campus (offline / online)
    @Override
    public void updateStatus(String id, Integer status) {
        //Query campus information by id
        Campus campus = campusRepository.findById(id).get();
        //set modified value
        campus.setStatus(status);
        campus.setUpdateTime(new Date());
        campusRepository.save(campus);
    }

    //
    @Override
    public Map<String, Object> getCampusById(String id) {
        Map<String, Object> result = new HashMap<>();
        Campus campus = this.setCampusCamType(campusRepository.findById(id).get());
        //basic info of campus
        result.put("campus", campus);
        //It is more intuitive to handle alone
        result.put("bookingRule", campus.getBookingRule());
        //No need to return repeatedly
        campus.setBookingRule(null);
        return result;
    }

    //Get campus name
    @Override
    public String getCampusName(String campuscode) {
        Campus campus = campusRepository.getCampusByCampuscode(campuscode);
        if (campus != null) {
            return campus.getCampusname();
        }
        return null;
    }

    //Search by campus name
    @Override
    public List<Campus> findByCampusName(String campusName) {
        return campusRepository.findCampusByCampusnameLike(campusName);
    }

    //Get campus appointment registration details based on campus number
    @Override
    public Map<String, Object> item(String campuscode) {
        Map<String, Object> result = new HashMap<>();
        //campus details
        Campus campus = this.setCampusCamType(this.getByCampuscode(campuscode));
        result.put("campus", campus);
        //Appointment Rules
        result.put("bookingRule", campus.getBookingRule());
        //No need to return repeatedly
        campus.setBookingRule(null);
        return result;
    }

    //
    //Get the query list collection, traverse for campus-level encapsulation
    private Campus setCampusCamType(Campus campus) {
        System.out.println("Campus: " + campus);
        //Get campus grade name based on dictCode and value
        String campustypeString = dictFeignClient.getName("Campustype", campus.getCampustype());
        System.out.println("campustypeString: " + campustypeString);
        String stateString = dictFeignClient.getName(campus.getStateCode());
        System.out.println("stateString: " + stateString);
        String cityString = dictFeignClient.getName(campus.getCityCode());
        System.out.println("cityString: " + cityString);
        String districtString = dictFeignClient.getName(campus.getDistrictCode());
        campus.getParam().put("fullAddress", stateString + cityString + districtString);
        campus.getParam().put("campustypeString", campustypeString);
        return campus;
    }


}
