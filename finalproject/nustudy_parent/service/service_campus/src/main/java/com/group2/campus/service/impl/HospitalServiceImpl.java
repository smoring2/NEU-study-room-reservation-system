package com.group2.campus.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.group2.campus.repository.HospitalRepository;
import com.group2.campus.service.HospitalService;
import com.group2.nustudy.model.hosp.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void save(Map<String, Object> paramMap) {
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);

        // check whether the data exist
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);

        // modify if exists
        if(hospitalExist != null) {
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        } else {// add
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }
    }

    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        return hospital;
    }
//
//    //医院列表(条件查询分页)
//    @Override
//    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
//        //创建pageable对象
//        Pageable pageable = PageRequest.of(page-1,limit);
//        //创建条件匹配器
//        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
//                .withIgnoreCase(true);
//        //hospitalSetQueryVo转换Hospital对象
//        Hospital hospital = new Hospital();
//        BeanUtils.copyProperties(hospitalQueryVo,hospital);
//        //创建对象
//        Example<Hospital> example = Example.of(hospital,matcher);
//        //调用方法实现查询
//        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);
//
//        //获取查询list集合，遍历进行医院等级封装
//        pages.getContent().stream().forEach(item -> {
//            this.setHospitalHosType(item);
//        });
//
//        return pages;
//    }
//
//    //更新医院上线状态
//    @Override
//    public void updateStatus(String id, Integer status) {
//        //根据id查询医院信息
//        Hospital hospital = hospitalRepository.findById(id).get();
//        //设置修改的值
//        hospital.setStatus(status);
//        hospital.setUpdateTime(new Date());
//        hospitalRepository.save(hospital);
//    }
//
//    @Override
//    public Map<String, Object> getHospById(String id) {
//        Map<String, Object> result = new HashMap<>();
//        Hospital hospital = this.setHospitalHosType(hospitalRepository.findById(id).get());
//        //医院基本信息（包含医院等级）
//        result.put("hospital",hospital);
//        //单独处理更直观
//        result.put("bookingRule", hospital.getBookingRule());
//        //不需要重复返回
//        hospital.setBookingRule(null);
//        return result;
//    }
//
//    //获取医院名称
//    @Override
//    public String getHospName(String hoscode) {
//        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
//        if(hospital != null) {
//            return hospital.getHosname();
//        }
//        return null;
//    }
//
//    //根据医院名称查询
//    @Override
//    public List<Hospital> findByHosname(String hosname) {
//        return hospitalRepository.findHospitalByHosnameLike(hosname);
//    }
//
//    //根据医院编号获取医院预约挂号详情
//    @Override
//    public Map<String, Object> item(String hoscode) {
//        Map<String, Object> result = new HashMap<>();
//        //医院详情
//        Hospital hospital = this.setHospitalHosType(this.getByHoscode(hoscode));
//        result.put("hospital", hospital);
//        //预约规则
//        result.put("bookingRule", hospital.getBookingRule());
//        //不需要重复返回
//        hospital.setBookingRule(null);
//        return result;
//    }
//
//    //获取查询list集合，遍历进行医院等级封装
//    private Hospital setHospitalHosType(Hospital hospital) {
//        //根据dictCode和value获取医院等级名称
//        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());
//        //查询省 市  地区
//        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
//        String cityString = dictFeignClient.getName(hospital.getCityCode());
//        String districtString = dictFeignClient.getName(hospital.getDistrictCode());
//
//        hospital.getParam().put("fullAddress",provinceString+cityString+districtString);
//        hospital.getParam().put("hostypeString",hostypeString);
//        return hospital;
//    }


}
