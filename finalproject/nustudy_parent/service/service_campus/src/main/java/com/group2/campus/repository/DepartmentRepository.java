package com.group2.campus.repository;


import com.group2.nustudy.model.camp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    //Upload department interface
    Department getDepartmentByCampuscodeAndDepcode(String campuscode, String depcode);
}
