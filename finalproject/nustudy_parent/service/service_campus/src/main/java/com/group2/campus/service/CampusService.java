package com.group2.campus.service;

import com.group2.nustudy.model.camp.Campus;
import com.group2.nustudy.vo.camp.CampusQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface CampusService {
    //Upload campus interface
    void save(Map<String, Object> paramMap);

    //Realize query according to campus number
    Campus getByCampuscode(String campuscode);

    //campus List (Conditional Query Pagination)
    Page<Campus> selectCampusPage(Integer page, Integer limit, CampusQueryVo campusQueryVo);

    //Update campus online status
    void updateStatus(String id, Integer status);

    //campus details
    Map<String, Object> getCampusById(String id);

    //Get campus name
    String getCampusName(String campuscode);

    //Search by campus name
    List<Campus> findByCampusName(String campusName);

    //Get campus appointment registration details based on campus number
    Map<String, Object> item(String campuscode);
}
