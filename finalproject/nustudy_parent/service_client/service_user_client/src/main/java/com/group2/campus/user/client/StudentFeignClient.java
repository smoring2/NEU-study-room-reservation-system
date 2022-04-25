package com.group2.campus.user.client;


import com.group2.nustudy.model.user.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user")
@Repository
public interface StudentFeignClient {

    //根据就诊人id获取就诊人信息
    @GetMapping("/api/user/student/inner/get/{id}")
    public Student getStudentOrder(@PathVariable("id") Long id);
}
