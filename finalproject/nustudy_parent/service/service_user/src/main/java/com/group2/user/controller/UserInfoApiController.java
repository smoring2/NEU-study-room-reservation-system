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


}
