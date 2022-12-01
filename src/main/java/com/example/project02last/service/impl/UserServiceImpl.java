package com.example.project02last.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.project02last.common.Constants;
import com.example.project02last.controller.dto.UserDTO;
import com.example.project02last.entity.Menu;
import com.example.project02last.entity.User;
import com.example.project02last.exception.ServiceException;
import com.example.project02last.mapper.RoleMapper;
import com.example.project02last.mapper.RoleMenuMapper;
import com.example.project02last.mapper.UserMapper;
import com.example.project02last.service.IMenuService;
import com.example.project02last.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.project02last.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource
    private IMenuService menuService;
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
    @Override
    public UserDTO login(UserDTO userDTO) {
        User one=getUserInfo(userDTO);
        if (one!=null){
            BeanUtil.copyProperties(one,userDTO,true);
            //参数1原对象 参数2就是就是把原对象的参数赋值到的新对象 字段不一样的会自动去掉
            //设置token通过User对象
            String token = TokenUtils.genToken(one.getId().toString(),one.getPassword());
            userDTO.setToken(token);


            String role= one.getRole();
            //找到登录角色的 角色id
            Integer roleId=roleMapper.selectByFlag(role);
            //根据roleId查出 role_menu中 该角色对应所有的菜单id集合
            List<Integer> menuIds=roleMenuMapper.selectByRoleId(roleId);
            //查出系统所有的菜单
            List<Menu>  menus=menuService.findMenus("");
            List<Menu>  roleMenus=new ArrayList<>();
            //筛选当前角色的菜单
            for (Menu menu : menus) {
                if (menuIds.contains(menu.getId())){
                    roleMenus.add(menu);
                }
                List<Menu> children =menu.getChildren();
                //移除children里面不在menuIds集合中的元素
                children.removeIf(child->!menuIds.contains(child.getId()));
            }

            userDTO.setMenus(roleMenus);
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


}
