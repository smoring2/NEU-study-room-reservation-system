package com.group2.campus.service;

import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.vo.camp.CampusQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CampusService {
    //Upload hospital interface
    void save(Map<String, Object> paramMap);

    //Realize query according to hospital number
    Campus getByCampuscode(String campuscode);

    //Hospital List (Conditional Query Pagination)
    Page<Campus> selectCampusPage(Integer page, Integer limit, CampusQueryVo campusQueryVo);

    //Update hospital online status
    void updateStatus(String id, Integer status);

    //hospital details
    Map<String, Object> getCampusById(String id);

    //Get hospital name
    String getCampusName(String campuscode);

    //Search by hospital name
    List<Campus> findByCampusName(String campusName);

    //Get hospital appointment registration details based on hospital number
    Map<String, Object> item(String campuscode);
}
