package com.group2.campus.config;

import com.group2.campus.util.NustudyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handling class
 *
 * @author qy
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String error(Exception e){
        e.printStackTrace();
        return "error";
    }

    /**
     * Custom exception handling method
     * @param e
     * @return
     */
    @ExceptionHandler(NustudyException.class)
    public String error(NustudyException e, Model model){
        model.addAttribute("message", e.getMessage());
        return "error";
    }
}
