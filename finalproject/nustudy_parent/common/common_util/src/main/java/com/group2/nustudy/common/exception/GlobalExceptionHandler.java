package com.group2.nustudy.common.exception;
import com.group2.nustudy.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * customer exception
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Get Exception error result
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * Get NustudyException error result
     * @param e
     * @return
     */
    @ExceptionHandler(NustudyException.class)
    @ResponseBody
    public Result error(NustudyException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
