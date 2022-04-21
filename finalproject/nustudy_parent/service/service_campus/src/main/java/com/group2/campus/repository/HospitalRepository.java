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
}
