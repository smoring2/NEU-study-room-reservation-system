package com.group2.nustudy.common.exception;
import com.group2.nustudy.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail();
    }

    @ExceptionHandler(NustudyException.class)
    @ResponseBody
    public Result error(NustudyException e) {
        e.printStackTrace();
        return Result.fail();
    }
}
