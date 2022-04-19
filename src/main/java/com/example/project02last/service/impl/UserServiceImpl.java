package com.example.project02last.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.project02last.common.Constants;
import com.example.project02last.controller.dto.UserDTO;
import com.example.project02last.entity.User;
import com.example.project02last.exception.ServiceException;
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
    public UserDTO login(UserDTO userDTO) {
        User one=getUserInfo(userDTO);
        if (one!=null){
            BeanUtil.copyProperties(one,userDTO,true);
            //参数1原对象 参数2就是就是把原对象的参数赋值到的新对象
            return userDTO;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }//所以这里用抛出异常的方式

    }

    @Override
    public User register(UserDTO userDTO) {
        User one=getUserInfo(userDTO);  //User one就是用userDTO查询的结果集
        if (one==null){
           one=new User();
           BeanUtil.copyProperties(userDTO,one,true);
           save(one);
        }
        else {
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
        return one;
    }

    private  User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",userDTO.getUsername());
        queryWrapper.eq("password",userDTO.getPassword());
        User one;
        try{
            one=getOne(queryWrapper);//查询用户信息赋值给one
            // 这里如果是getone如果数据库有两个用户名和密码一样的数据就会报错
        }catch (Exception e){
            log.error(e);//打印异常的log
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }
}
