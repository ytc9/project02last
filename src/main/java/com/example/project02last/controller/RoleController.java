package com.example.project02last.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project02last.common.Result;
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
public Result save(@RequestBody Role role) {
        return Result.success(roleService.saveOrUpdate(role));
        }

@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
        return Result.success(roleService.removeById(id)) ;
        }

@PostMapping("/del/batch") //Delet接口没办法从前端传纯数组所以要用post接口
public Result deleteBatch(@RequestBody List<Integer> ids){  //[1,2,3]
        return Result.success(roleService.removeByIds(ids)) ;
        }

@GetMapping
public Result findAll() {
        return Result.success(roleService.list()) ;
        }

@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(roleService.getById(id)) ;
        }

@GetMapping("/page")
public Result findPage(@RequestParam String name,
                       @RequestParam Integer pageNum,
                       @RequestParam Integer pageSize) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.orderByDesc("id");
        return Result.success(roleService.page(new Page<>(pageNum, pageSize),queryWrapper)) ;
}

@PostMapping("/roleMenu/{roleId}")
public Result roleMenu(@PathVariable Integer roleId,@RequestBody List<Integer> menuIds) {
        roleService.setRoleMenu(roleId,menuIds);
        return  Result.success();
}
@GetMapping("/roleMenu/{roleId}")
public Result GetRoleMenu(@PathVariable Integer roleId) {
        return  Result.success( roleService.getRoleMenu(roleId));
}
}

