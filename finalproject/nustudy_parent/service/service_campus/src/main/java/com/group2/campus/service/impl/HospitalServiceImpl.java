package com.group2.campus.service.impl;

import com.group2.campus.repository.HospitalRepository;
import com.group2.campus.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;
}
