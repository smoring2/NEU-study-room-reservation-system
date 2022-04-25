//package com.group2.user.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.group2.campus.cmn.client.DictFeignClient;
//import com.group2.nustudy.enums.DictEnum;
//import com.group2.nustudy.model.user.Student;
//import com.group2.user.mapper.StudentMapper;
//import com.group2.user.service.StudentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class StudentServiceImpl extends
//        ServiceImpl<StudentMapper, Student> implements StudentService {
//
//    @Autowired
//    private DictFeignClient dictFeignClient;
//
//    //获取就诊人列表
//    @Override
//    public List<Student> findAllUserId(Long userId) {
//        //根据userid查询所有就诊人信息列表
//        QueryWrapper<Student> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id",userId);
//        List<Student> patientList = baseMapper.selectList(wrapper);
//        //通过远程调用，得到编码对应具体内容，查询数据字典表内容
//        patientList.stream().forEach(item -> {
//            //其他参数封装
//            this.packPatient(item);
//        });
//        return patientList;
//    }
//
//    @Override
//    public Student getStudentId(Long id) {
//        return this.packPatient(baseMapper.selectById(id));
//    }
//
//    //Patient对象里面其他参数封装
//    private Student packPatient(Student student) {
//        //根据证件类型编码，获取证件类型具体指
//        String certificatesTypeString =
//                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), student.getCertificatesType());//联系人证件
//        //联系人证件类型
//        String contactsCertificatesTypeString =
//                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(),student.getContactsCertificatesType());
//        //省
//        String provinceString = dictFeignClient.getName(student.getProvinceCode());
//        //市
//        String cityString = dictFeignClient.getName(student.getCityCode());
//        //区
//        String districtString = dictFeignClient.getName(student.getDistrictCode());
//
//        student.getParam().put("certificatesTypeString", certificatesTypeString);
//        student.getParam().put("contactsCertificatesTypeString", contactsCertificatesTypeString);
//        student.getParam().put("provinceString", provinceString);
//        student.getParam().put("cityString", cityString);
//        student.getParam().put("districtString", districtString);
//        student.getParam().put("fullAddress", provinceString + cityString + districtString + student.getAddress());
//        return student;
//    }
//}
