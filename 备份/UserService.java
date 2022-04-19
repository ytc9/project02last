package com.example.project02last.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project02last.entity.User;
import com.example.project02last.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//这里继承了mybatis的实现类
@Service
public class UserService extends ServiceImpl<UserMapper,User> {
    public boolean saveUser(User user) {
        /*if (user.getId()==null){
            return save(user);
        }else {
            return updateById(user);
        }   mybatis-plus 提供的save方法和下面一样
        saveOrUpdate就是 封装好的save方法
        */
        return saveOrUpdate(user);
    }

//service其实就是写一个逻辑函数去实现UserMapper里面方法

   /* @Autowired
    private UserMapper userMapper;
    public  int save(User user)
    {
        if (user.getId()==null){
            return userMapper.insert(user);
        }else {
            return userMapper.update(user);
        }
    }*/
}
