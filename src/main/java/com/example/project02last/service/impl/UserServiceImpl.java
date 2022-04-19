package com.example.project02last.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.project02last.controller.dto.UserDTO;
import com.example.project02last.entity.User;
import com.example.project02last.mapper.UserMapper;
import com.example.project02last.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author 杨添辰
 * @since 2022-04-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log log=Log.get();
    @Override
    public Boolean login(UserDTO userDTO) {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        //判断数据库里面的 User里面即数据库里的数据和前端接收的数据是否相等
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        try{
            User one=getOne(queryWrapper);//这里如果是getone如果数据库有两个用户名和密码一样的数据就会报错
            return one!=null;  //所以这里用抛出异常的方式
        }catch (Exception e){
            log.error(e);//打印异常的log
            return false;
        }


    }
}
