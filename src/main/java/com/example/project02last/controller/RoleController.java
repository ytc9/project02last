package com.example.project02last.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

//这里要配置模板才能生产对应的controller

import com.example.project02last.service.IRoleService;
import com.example.project02last.entity.Role;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨添辰
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/role")
        public class RoleController {

@Resource
private IRoleService roleService;

@PostMapping
public Boolean save(@RequestBody Role role) {
        return roleService.saveOrUpdate(role);
        }

@DeleteMapping("/{id}")
public Boolean delete(@PathVariable Integer id) {
        return roleService.removeById(id);
        }

@PostMapping("/del/batch") //Delet接口没办法从前端传纯数组所以要用post接口
public boolean deleteBatch(@RequestBody List<Integer> ids){  //[1,2,3]
        return roleService.removeByIds(ids);
        }

@GetMapping
public List<Role> findAll() {
        return roleService.list();
        }

@GetMapping("/{id}")
public Role findOne(@PathVariable Integer id) {
        return roleService.getById(id);
        }

@GetMapping("/page")
public Page<Role> findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return roleService.page(new Page<>(pageNum, pageSize),queryWrapper);
}
}

