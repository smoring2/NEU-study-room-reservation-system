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
        //创建条件匹配器
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
        //根据id查询医院信息
        Campus campus = campusRepository.findById(id).get();
        //设置修改的值
        campus.setStatus(status);
        campus.setUpdateTime(new Date());
        campusRepository.save(campus);
    }

    //
    @Override
    public Map<String, Object> getCampusById(String id) {
        Map<String, Object> result = new HashMap<>();
        Campus campus = this.setCampusCamType(campusRepository.findById(id).get());
        //医院基本信息（包含医院等级）
        result.put("campus", campus);
        //单独处理更直观
        result.put("bookingRule", campus.getBookingRule());
        //不需要重复返回
        campus.setBookingRule(null);
        return result;
    }

    //获取医院名称
    @Override
    public String getCampusName(String campuscode) {
        Campus campus = campusRepository.getCampusByCampuscode(campuscode);
        if (campus != null) {
            return campus.getCampusname();
        }
        return null;
    }

    //根据医院名称查询
    @Override
    public List<Campus> findByCampusName(String campusName) {
        return campusRepository.findCampusByCampusnameLike(campusName);
    }

    //根据医院编号获取医院预约挂号详情
    @Override
    public Map<String, Object> item(String campuscode) {
        Map<String, Object> result = new HashMap<>();
        //医院详情
        Campus campus = this.setCampusCamType(this.getByCampuscode(campuscode));
        result.put("campus", campus);
        //预约规则
        result.put("bookingRule", campus.getBookingRule());
        //不需要重复返回
        campus.setBookingRule(null);
        return result;
    }

    //
    //获取查询list集合，遍历进行医院等级封装
    private Campus setCampusCamType(Campus campus) {
        System.out.println("Campus: " + campus);
        //根据dictCode和value获取医院等级名称
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
