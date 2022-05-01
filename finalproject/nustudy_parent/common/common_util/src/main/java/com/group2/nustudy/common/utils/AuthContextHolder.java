package com.group2.nustudy.common.utils;


import com.group2.nustudy.common.helper.JwtHelper;

import javax.servlet.http.HttpServletRequest;


/**
 * Get the current user info
 */
public class AuthContextHolder {

    /**
     * Get current user id
     * @param request
     * @return
     */
    public static Long getUserId(HttpServletRequest request) {
        //get token from header
        String token = request.getHeader("token");
        System.out.println("token: " + token);
        // jwt get userid from token
        Long userId = JwtHelper.getUserId(token);
        System.out.println("userid: " + userId);
        return userId;
    }

    /**
     * Get current user name
     * @param request
     * @return
     */
    public static String getUserName(HttpServletRequest request) {
        //get token from header
        String token = request.getHeader("token");
        // jwt get userid from token
        String userName = JwtHelper.getUserName(token);
        return userName;
    }
}
