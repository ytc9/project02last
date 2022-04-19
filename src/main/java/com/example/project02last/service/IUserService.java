package com.example.project02last.service;

import com.example.project02last.controller.dto.UserDTO;
import com.example.project02last.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨添辰
 * @since 2022-04-17
 */
public interface IUserService extends IService<User> {
    Boolean login(UserDTO userDTO);
}
