package com.group2.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.group2.nustudy.common.exception.NustudyException;
import com.group2.nustudy.common.helper.JwtHelper;
import com.group2.nustudy.common.result.ResultCodeEnum;
import com.group2.nustudy.enums.AuthStatusEnum;
import com.group2.nustudy.model.user.Student;
import com.group2.nustudy.model.user.UserInfo;
import com.group2.nustudy.vo.user.LoginVo;
import com.group2.nustudy.vo.user.UserAuthVo;
import com.group2.nustudy.vo.user.UserInfoQueryVo;
import com.group2.user.mapper.UserInfoMapper;
import com.group2.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoServiceImpl  extends
        ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Autowired
    private UserInfoService userInfoService;

    //User mobile phone number login interface
    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {
        //Get the entered mobile phone number and verification code from loginVo
        String email = loginVo.getEmail();
        String code = loginVo.getCode();

        //Determine whether the mobile phone number and verification code are empty
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(code)) {
            throw new NustudyException(ResultCodeEnum.PARAM_ERROR);
        }



        //Bind mobile number
        UserInfo userInfo = null;


        //If userinfo is empty, perform normal mobile login
        if (userInfo == null) {
            //Determine whether it is the first login: query the database according to the mobile phone number, if there is no same mobile phone number, it is the first login
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("email", email);
            userInfo = baseMapper.selectOne(wrapper);
            if (userInfo == null) { //first time
                //add info to DB
                userInfo = new UserInfo();
                userInfo.setName("");
                userInfo.setEmail(email);
                userInfo.setStatus(1);
                baseMapper.insert(userInfo);
            }
        }

        //Check if disabled
        if (userInfo.getStatus() == 0) {
            throw new NustudyException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //Not the first time, log in directly
        //return login information
        //return the login username
        //return token information
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if (StringUtils.isEmpty(name)) {
            name = userInfo.getEmail();
        }
        map.put("name", name);

        //jwt generates token string
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);
        return map;
    }

}
