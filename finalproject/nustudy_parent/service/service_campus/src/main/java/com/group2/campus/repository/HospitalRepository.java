package com.group2.campus.repository;

import com.group2.nustudy.model.hosp.Campus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepository extends MongoRepository<Campus,String> {
    //判断是否存在数据
    Campus getHospitalByHoscode(String hoscode);

    //根据医院名称查询
    List<Campus> findHospitalByHosnameLike(String hosname);
}
