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

//没有特殊需求sql比较常见的sql都可以用mybatis plus的接口直接实现
//mybatis-plus每个接口类必须用继承的方式绑定bean类型就可以实现通过bean类去实现sql操作
public interface IUserService extends IService<User> {
    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);
}
