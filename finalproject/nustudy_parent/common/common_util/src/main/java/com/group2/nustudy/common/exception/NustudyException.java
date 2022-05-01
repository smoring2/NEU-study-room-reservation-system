package com.group2.nustudy.common.exception;

import com.group2.nustudy.common.result.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Globally exception
 *
 * @author YT
 */
@Data
@ApiModel(value = "Globally exception")
public class NustudyException extends RuntimeException {

    @ApiModelProperty(value = "exception code")
    private Integer code;

    /**
     * Get exception by exception code and error message
     * @param message
     * @param code
     */
    public NustudyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * Enum
     * @param resultCodeEnum
     */
    public NustudyException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "NustudyException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
