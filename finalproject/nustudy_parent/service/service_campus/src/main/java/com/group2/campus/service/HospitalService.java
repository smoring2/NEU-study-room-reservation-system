package com.group2.campus.service;

import com.group2.nustudy.model.hosp.Hospital;

import java.util.Map;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
public interface HospitalService {

    void save(Map<String, Object> paramMap);

    Hospital getByHoscode(String hoscode);
}
