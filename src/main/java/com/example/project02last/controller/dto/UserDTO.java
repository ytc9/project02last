package com.example.project02last.controller.dto;

import com.example.project02last.entity.Menu;
import lombok.Data;

import java.util.List;

//接受前端登录请求的bean类
@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String token;
    private String role;
    private List<Menu> menus;
}
