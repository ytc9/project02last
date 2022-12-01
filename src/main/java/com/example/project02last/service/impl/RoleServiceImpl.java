package com.example.project02last.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.project02last.entity.Menu;
import com.example.project02last.entity.Role;
import com.example.project02last.entity.RoleMenu;
import com.example.project02last.mapper.RoleMapper;
import com.example.project02last.mapper.RoleMenuMapper;
import com.example.project02last.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 杨添辰
 * @since 2022-04-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Resource MenuServiceImpl menuService;

    @Transactional
    @Override
    //通过传来的中间表的映射数据来填充关系
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        //先删除所有的角色id所有的绑定关系
        roleMenuMapper.delete(queryWrapper);



        for (Integer menuId:menuIds)
        {
            Menu menu =menuService.getById(menuId);
            //如果pid!=null代表是二级菜单 并且 传过来的ids中没有父级id
            if (menu.getPid()!=null&& !menuIds.contains(menu.getPid())) {
                 //那么我们就得补上父级id因为前端没法传给父级id
                RoleMenu roleMenu=new RoleMenu();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                roleMenuMapper.insert(roleMenu);

            }
            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Override
    public List<Integer> getRoleMenu(Integer roleId) {
        return roleMenuMapper.selectByRoleId(roleId);
    }
}
