package com.group2.campus.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Custom global exception class
 */
@Data
@ApiModel(value = "Custom global exception class")
public class NustudyException extends RuntimeException {

    @ApiModelProperty(value = "exception status code")
    private Integer code;

    /**
     * Create exception object with status code and error message
     * @param message
     * @param code
     */
    public NustudyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public NustudyException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
