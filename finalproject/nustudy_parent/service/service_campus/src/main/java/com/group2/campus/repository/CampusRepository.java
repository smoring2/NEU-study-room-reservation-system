package com.group2.campus.repository;

import com.group2.nustudy.model.camp.Campus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampusRepository extends MongoRepository<Campus,String> {
    //Determine if there is data
    Campus getCampusByCampuscode(String campuscode);

    //Search by hospital name
    List<Campus> findCampusByCampusnameLike(String campusName);
}

