package com.group2.user.api;


import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.common.utils.AuthContextHolder;
import com.group2.nustudy.model.user.Student;
import com.group2.user.service.StudentService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//Student management interface
@RestController
@RequestMapping("/api/user/student")
public class StudentApiController {

    @Autowired
    private StudentService studentService;

    //Get a list of students
    @GetMapping("auth/findAll")
    public Result findAll(HttpServletRequest request) {
        //Get the currently logged in user id
        Long userId = AuthContextHolder.getUserId(request);
        System.out.println("userid: " + userId);
        List<Student> list = studentService.findAllUserId(userId);
//        Student student = studentService.findStudentWithUserId(userId);
        System.out.println("list: " + list);
        return Result.ok(list);
    }

    //Add a student
    @PostMapping("auth/save")
    public Result saveStudent(@RequestBody Student student, HttpServletRequest request) {
        //Get the currently logged in user id
        Long userId = AuthContextHolder.getUserId(request);
        student.setUserId(userId);
        studentService.save(student);
        return Result.ok();
    }

    //Get student information based on id
    @GetMapping("auth/get/{id}")
    public Result getStudent(@PathVariable Long id) {
        Student student = studentService.getStudentId(id);
        return Result.ok(student);
    }

    //Modify student
    @PostMapping("auth/update")
    public Result updateStudent(@RequestBody Student student) {
        studentService.updateById(student);
        return Result.ok();
    }

    //delete student
    @DeleteMapping("auth/remove/{id}")
    public Result removeStudent(@PathVariable Long id) {
        studentService.removeById(id);
        return Result.ok();
    }

    //Obtain student information based on student id
    @GetMapping("inner/get/{id}")
    public Student getStudentOrder(
            @ApiParam(name="id", value = "student id", required = true)
            @PathVariable Long id) {
        Student student = studentService.getStudentId(id);
        return student;
    }
}
