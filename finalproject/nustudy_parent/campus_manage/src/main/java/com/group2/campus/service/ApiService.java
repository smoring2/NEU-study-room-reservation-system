package com.group2.campus.service;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Map;

public interface ApiService {

    String getCampuscode();

    String getSignKey();

    JSONObject getCampus();

    boolean saveCampus(String data);

    Map<String, Object> findDepartment(int pageNum, int pageSize);

    boolean saveDepartment(String data);

    boolean removeDepartment(String depcode);

    Map<String, Object> findSchedule(int pageNum, int pageSize);

    boolean saveSchedule(String data);

    boolean removeSchedule(String hosScheduleId);

    void saveBatchCampus() throws IOException;
}
