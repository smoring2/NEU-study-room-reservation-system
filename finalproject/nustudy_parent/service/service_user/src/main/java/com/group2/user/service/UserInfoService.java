package com.group2.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.group2.nustudy.model.user.UserInfo;
import com.group2.nustudy.vo.user.LoginVo;
import com.group2.nustudy.vo.user.UserAuthVo;
import com.group2.nustudy.vo.user.UserInfoQueryVo;

import java.util.Map;

public interface UserInfoService {// extends IService<UserInfo> {
    //User mobile phone number login interface
    Map<String, Object> loginUser(LoginVo loginVo);

    //from openid to judge
//    UserInfo selectWxInfoOpenId(String openid);

//    //user identification
//    void userAuth(Long userId, UserAuthVo userAuthVo);
//
//    //user list）
//    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);
//
//    //user lockout
//    void lock(Long userId, Integer status);

    //user detail
//    Map<String, Object> show(Long userId);
//
//    //varification
//    void approval(Long userId, Integer authStatus);
}
