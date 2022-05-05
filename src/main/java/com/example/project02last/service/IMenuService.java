package com.example.project02last.service;

import com.example.project02last.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 杨添辰
 * @since 2022-04-27
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> findAll(String name);
}
