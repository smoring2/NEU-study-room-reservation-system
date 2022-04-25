package com.group2.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.nustudy.model.user.Student;

import java.util.List;

public interface StudentService extends IService<Student> {

    List<Student> findAllUserId(Long userId);


    Student getStudentId(Long id);
}
