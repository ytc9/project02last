package com.example.project02last.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.project02last.common.Result;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

//这里要配置模板才能生产对应的controller

import com.example.project02last.service.IMenuService;
import com.example.project02last.entity.Menu;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 杨添辰
 * @since 2022-04-27
 */
@RestController
@RequestMapping("/menu")

public class MenuController {

@Resource
private IMenuService menuService;

@PostMapping
public Result save(@RequestBody Menu menu) {
        return Result.success(menuService.saveOrUpdate(menu));
        }

@DeleteMapping("/{id}")
public Result delete(@PathVariable Integer id) {
        return Result.success(menuService.removeById(id));
        }

@PostMapping("/del/batch") //Delet接口没办法从前端传纯数组所以要用post接口
public Result deleteBatch(@RequestBody List<Integer> ids){  //[1,2,3]
        return Result.success(menuService.removeByIds(ids));
        }

@GetMapping
public Result findAll() {
         return Result.success(menuService.list());
        }

@GetMapping("/{id}")
public Result findOne(@PathVariable Integer id) {
        return Result.success(menuService.getById(id));
        }

@GetMapping("/page")
public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        return Result.success(menuService.page(new Page<>(pageNum, pageSize),queryWrapper)) ;
        }
}

