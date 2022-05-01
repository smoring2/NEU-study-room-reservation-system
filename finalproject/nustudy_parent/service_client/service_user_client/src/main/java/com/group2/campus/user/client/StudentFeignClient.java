package com.group2.campus.user.client;


import com.group2.nustudy.model.user.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * FeignClient of service-user
 */
@FeignClient(value = "service-user")
@Repository
public interface StudentFeignClient {

    //get student information by id
    @GetMapping("/api/user/student/inner/get/{id}")
    public Student getStudentOrder(@PathVariable("id") Long id);
}
