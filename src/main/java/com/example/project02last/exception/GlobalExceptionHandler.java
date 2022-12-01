package com.example.project02last.exception;

import com.example.project02last.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//自定义springboot异常
@ControllerAdvice
public class GlobalExceptionHandler {
    //必须要@ExceptionHandler(自定义异常类)去抛出异常
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return  Result.error(se.getCode(),se.getMessage());
    }

}
