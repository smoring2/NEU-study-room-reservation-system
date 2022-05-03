package com.group2.campus.util;
import lombok.Getter;

/**
 * Unified return result status information class
 *
 * @author qy
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"success"),
    FAIL(201, "fail"),
    SERVICE_ERROR(202, "Service exception"),
    DATA_ERROR(204, "DATA_ERROR"),

    SIGN_ERROR(300, "Signature error"),

    PAY_PASSWORD_ERROR(401, "wrong payment password"),
    REPEAT_ERROR(402, "repeated submit"),

    INVEST_AMMOUNT_MORE_ERROR(501, "The loan amount has exceeded the target amount"),
    RETURN_AMMOUNT_MORE_ERROR(502, "Incorrect repayment amount"),
    PROJECT_AMMOUNT_ERROR(503, "The target amount does not match")
    ;

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
