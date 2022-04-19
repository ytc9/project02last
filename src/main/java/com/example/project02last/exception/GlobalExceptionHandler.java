package com.example.project02last.exception;

import com.example.project02last.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//自定义springboot异常
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException.class)

    @ResponseBody
    public Result handle(ServiceException se){
        return  Result.error(se.getCode(),se.getMessage());
    }

}
