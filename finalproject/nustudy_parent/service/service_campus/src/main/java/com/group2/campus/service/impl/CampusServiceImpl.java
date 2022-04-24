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
        String hoscode = campus.getHoscode();
        Campus campusExist = campusRepository.getCampusByHoscode(hoscode);

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
    public Campus getByHoscode(String hoscode) {
        Campus campus = campusRepository.getCampusByHoscode(hoscode);
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
        //hospitalSetQueryVo转换Hospital对象
        Campus campus = new Campus();
        BeanUtils.copyProperties(campusQueryVo, campus);
        //创建对象
        Example<Campus> example = Example.of(campus, matcher);
        //调用方法实现查询
//        Page<Campus> pages = hospitalRepository.findAll(example, pageable);
        Page<Campus> pages = campusRepository.findAll(pageable);

        System.out.println("Hosps: " + pages.getSize() + ", " + pages.getTotalElements());
        //获取查询list集合，遍历进行医院等级封装
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
        result.put("hospital", campus);
        //单独处理更直观
        result.put("bookingRule", campus.getBookingRule());
        //不需要重复返回
        campus.setBookingRule(null);
        return result;
    }

    //获取医院名称
    @Override
    public String getCampusName(String hoscode) {
        Campus campus = campusRepository.getCampusByHoscode(hoscode);
        if (campus != null) {
            return campus.getHosname();
        }
        return null;
    }

    //根据医院名称查询
    @Override
    public List<Campus> findByCampusName(String campusName) {
        return campusRepository.findCampusByHosnameLike(campusName);
    }

    //根据医院编号获取医院预约挂号详情
    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> result = new HashMap<>();
        //医院详情
        Campus campus = this.setCampusCamType(this.getByHoscode(hoscode));
        result.put("hospital", campus);
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
        String hostypeString = dictFeignClient.getName("Hostype", campus.getHostype());
        System.out.println("hostypeString: " + hostypeString);
        //查询省 市  地区
        String provinceString = dictFeignClient.getName(campus.getProvinceCode());
        System.out.println("provinceString: " + provinceString);
        String cityString = dictFeignClient.getName(campus.getCityCode());
        System.out.println("cityString: " + cityString);
        String districtString = dictFeignClient.getName(campus.getDistrictCode());
        campus.getParam().put("fullAddress", provinceString + cityString + districtString);
        campus.getParam().put("hostypeString", hostypeString);
        return campus;
    }


}
