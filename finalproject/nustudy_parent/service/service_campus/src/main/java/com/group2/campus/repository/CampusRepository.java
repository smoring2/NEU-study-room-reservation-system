package com.group2.campus.repository;

import com.group2.nustudy.model.camp.Campus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusRepository extends MongoRepository<Campus,String> {
    //判断是否存在数据
    Campus getCampusByHoscode(String hoscode);

    //根据医院名称查询
    List<Campus> findCampusByHosnameLike(String hosname);
}