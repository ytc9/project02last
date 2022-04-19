package com.example.project02last.controller.dto;

import lombok.Data;

//接受前端登录请求的bean类
@Data
public class UserDTO {
    private String username;
    private String password;
}
