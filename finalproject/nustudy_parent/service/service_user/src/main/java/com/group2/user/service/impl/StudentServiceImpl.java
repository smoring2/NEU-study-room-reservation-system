package com.group2.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.campus.cmn.client.DictFeignClient;
import com.group2.nustudy.enums.DictEnum;
import com.group2.nustudy.model.user.Student;
import com.group2.user.mapper.StudentMapper;
import com.group2.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends
        ServiceImpl<StudentMapper, Student> implements StudentService {

    @Autowired
    private DictFeignClient dictFeignClient;

    //Get a list of students
    @Override
    public List<Student> findAllUserId(Long userId) {
        //Query a list of all student information based on userid
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<Student> studentList = baseMapper.selectList(wrapper);

        return studentList;
    }

    @Override
    public Student getStudentId(Long id) {

        return baseMapper.selectById(id);
    }

    //Student: Other parameter encapsulation in the object
    private Student packStudent(Student student) {
        //According to the certificate type code, obtaining the certificate type specifically refers to
        String certificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), student.getCertificatesType());//联系人证件

        //state
        String stateString = dictFeignClient.getName(student.getStateCode());
        //city
        String cityString = dictFeignClient.getName(student.getCityCode());
        //district
        String districtString = dictFeignClient.getName(student.getDistrictCode());

        student.getParam().put("certificatesTypeString", certificatesTypeString);
        student.getParam().put("stateString", stateString);
        student.getParam().put("cityString", cityString);
        student.getParam().put("districtString", districtString);
        student.getParam().put("fullAddress", stateString + cityString + districtString + student.getAddress());
        System.out.println("userid: " + student.getUserId());
        return student;
    }
}
