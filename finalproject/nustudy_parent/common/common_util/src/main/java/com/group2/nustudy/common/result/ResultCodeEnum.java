package com.group2.nustudy.common.result;

import lombok.Getter;

/**
 * 统一返回结果状态信息类
 */
@Getter
public enum ResultCodeEnum {
// DEBUG TRANSLATE
    SUCCESS(200,"Succeed"),
    FAIL(201, "Failed"),
    PARAM_ERROR( 202, "The param is wrong"),
    SERVICE_ERROR(203, "SERVICE ERROR"),
    DATA_ERROR(204, "DATA ERROR"),
    DATA_UPDATE_ERROR(205, "DATA_UPDATE_ERROR"),

    LOGIN_AUTH(208, "Please log in"),
    PERMISSION(209, "No Permission"),

    CODE_ERROR(210, "CODE ERROR"),
//    LOGIN_MOBLE_ERROR(211, "账号不正确"),
    LOGIN_DISABLED_ERROR(212, "LOGIN_DISABLED_ERROR"),
    REGISTER_MOBLE_ERROR(213, "REGISTER_MOBLE_ERROR"),
    LOGIN_AURH(214, "LOGIN_AURH"),
    LOGIN_ACL(215, "LOGIN_ACL"),

    URL_ENCODE_ERROR( 216, "URL_ENCODE_ERROR"),
    ILLEGAL_CALLBACK_REQUEST_ERROR( 217, "ILLEGAL_CALLBACK_REQUEST_ERROR"),
    FETCH_ACCESSTOKEN_FAILD( 218, "FETCH_ACCESSTOKEN_FAILD"),
    FETCH_USERINFO_ERROR( 219, "FETCH_USERINFO_ERROR"),
    //LOGIN_ERROR( 23005, "登录失败"),

    PAY_RUN(220, "PAY_RUN"),
    CANCEL_ORDER_FAIL(225, "CANCEL_ORDER_FAIL"),
    CANCEL_ORDER_NO(225, "CANCEL_ORDER_NO"),

    HOSCODE_EXIST(230, "HOSCODE_EXIST"),
    NUMBER_NO(240, "NUMBER_NO"),
    TIME_NO(250, "TIME_NO"),

    SIGN_ERROR(300, "SIGN_ERROR"),
    CAMPUS_OPEN(310, "CAMPUS_NOT_OPEN"),
    CAMPUS_LOCK(320, "CAMPUS_LOCK"),
    ;

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
