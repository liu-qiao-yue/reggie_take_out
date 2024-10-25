package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Setmeal;

public interface SetmealService extends IService<Setmeal> {

    int getCountByCategoryId(Long categoryId);

    Page<Setmeal> setmealPageList(Integer page, Integer pageSize, String name);
}
