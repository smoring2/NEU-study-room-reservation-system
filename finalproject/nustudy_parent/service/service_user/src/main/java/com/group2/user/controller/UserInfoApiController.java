package com.group2.user.controller;

import com.group2.nustudy.common.result.Result;
import com.group2.nustudy.common.utils.AuthContextHolder;
import com.group2.nustudy.model.user.UserInfo;
import com.group2.nustudy.vo.user.LoginVo;
import com.group2.nustudy.vo.user.UserAuthVo;
import com.group2.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    //User mobile phone number login interface
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        System.out.println(loginVo);
        Map<String,Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

    //User Authentication Interface
//    @PostMapping("auth/userAuth")
//    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request) {
//        //Pass two parameters, the first parameter user id, the second parameter authentication data vo object
//        userInfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
//        return Result.ok();
//    }

    //Get user id information interface
//    @GetMapping("auth/getUserInfo")
//    public Result getUserInfo(HttpServletRequest request) {
//        Long userId = AuthContextHolder.getUserId(request);
//        UserInfo userInfo = userInfoService.getById(userId);
//        return Result.ok(userInfo);
//    }
}
