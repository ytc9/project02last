package com.example.project02last.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.project02last.entity.Menu;
import com.example.project02last.mapper.MenuMapper;
import com.example.project02last.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 * @author 杨添辰
 * @since 2022-04-27
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findAll(String name) {
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        if (StrUtil.isNotBlank(name)){
            queryWrapper.like("name",name);//传来的name如果是空就全查
        }
        List<Menu> list=list(queryWrapper);
        //pid为null的1级菜单
        List<Menu> parentNodes=list.stream().filter(menu -> menu.getPid()==null).collect(Collectors.toList());
        for(Menu menu:parentNodes){
            //通过pid和id的对应值找出1级菜单的子菜单
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid()) ).collect(Collectors.toList()));
        }
        return parentNodes;
    }
}
