package com.group2.campus.repository;

import com.group2.nustudy.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {

    // check whether the data existgetHospitalByHoscode(String hoscode);
    Hospital getHospitalByHoscode(String hoscode); // no need to implement the method, since MongoRepository will help us to implement it based on its standard
}
