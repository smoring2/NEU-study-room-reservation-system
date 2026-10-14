package com.group2.campus.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Custom global exception class
 */
@Data
@Schema(description = "Custom global exception class")
public class NustudyException extends RuntimeException {

    @Schema(description = "exception status code")
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
