package com.group2.user.api;


import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.common.utils.AuthContextHolder;
import com.group2.nustudy.model.user.Student;
import com.group2.user.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//就诊人管理接口
@RestController
@RequestMapping("/api/user/student")
public class StudentApiController {

    @Autowired
    private StudentService studentService;

    //获取就诊人列表
    @GetMapping("auth/findAll")
    public Result findAll(HttpServletRequest request) {
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        System.out.println("userid: " + userId);
        List<Student> list = studentService.findAllUserId(userId);
//        Student student = studentService.findStudentWithUserId(userId);
        System.out.println("list: " + list);
        return Result.ok(list);
    }

    //添加就诊人
    @PostMapping("auth/save")
    public Result saveStudent(@RequestBody Student student, HttpServletRequest request) {
        //获取当前登录用户id
        Long userId = AuthContextHolder.getUserId(request);
        student.setUserId(userId);
        studentService.save(student);
        return Result.ok();
    }

    //根据id获取就诊人信息
    @GetMapping("auth/get/{id}")
    public Result getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentId(id);
        return Result.ok(student);
    }

    //修改就诊人
    @PostMapping("auth/update")
    public Result updateStudent(@RequestBody Student student) {
        studentService.updateById(student);
        return Result.ok();
    }

    //删除就诊人
    @DeleteMapping("auth/remove/{id}")
    public Result removeStudent(@PathVariable Long id) {
        studentService.removeById(id);
        return Result.ok();
    }

    //根据就诊人id获取就诊人信息
    @GetMapping("inner/get/{id}")
    public Student getStudentOrder(@PathVariable Long id) {
        Student student = studentService.getStudentId(id);
        return student;
    }
}
