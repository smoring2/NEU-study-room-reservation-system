//package com.group2.user.controller;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.group2.nustudy.common.result.Result;
//import com.group2.nustudy.model.user.UserInfo;
//import com.group2.nustudy.vo.user.UserInfoQueryVo;
//import com.group2.user.service.StudentService;
//import com.group2.user.service.UserInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/admin/user")
//public class StudentController {
//
//    @Autowired
//    private StudentService studentService;
//
//    //User list (conditional query with pagination)
//    @GetMapping("{page}/{limit}")
//    public Result list(@PathVariable Long page,
//                       @PathVariable Long limit,
//                       UserInfoQueryVo userInfoQueryVo) {
//        Page<UserInfo> pageParam = new Page<>(page,limit);
//        IPage<UserInfo> pageModel =
//                studentService.selectPage(pageParam,userInfoQueryVo);
//        return Result.ok(pageModel);
//    }
//
//    //User lockout
//    @GetMapping("lock/{userId}/{status}")
//    public Result lock(@PathVariable Long userId,@PathVariable Integer status) {
//        studentService.lock(userId,status);
//        return Result.ok();
//    }
//
//    //User details
//    @GetMapping("show/{userId}")
//    public Result show(@PathVariable Long userId) {
//        Map<String,Object> map = userInfoService.show(userId);
//        return Result.ok(map);
//    }
//
//    //Certification approval
//    @GetMapping("approval/{userId}/{authStatus}")
//    public Result approval(@PathVariable Long userId,@PathVariable Integer authStatus) {
//        userInfoService.approval(userId,authStatus);
//        return Result.ok();
//    }
//}
