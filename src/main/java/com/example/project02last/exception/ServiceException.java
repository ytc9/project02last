package com.example.project02last.exception;

import lombok.Getter;

//继承runtimeException
@Getter
public class ServiceException extends RuntimeException{
    private  String code;

    public ServiceException(String code,String msg){
        super(msg);
        this.code=code;
    }
}
