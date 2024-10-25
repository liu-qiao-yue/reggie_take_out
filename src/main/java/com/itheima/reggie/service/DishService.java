package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    int getCountByCategoryId(Long categoryId);

    Page<Dish> dishPageList(Integer page, Integer pageSize, String name);

    boolean updateDishStatus(Integer status, String ids);

    boolean saveDishInfos(DishDto dto);
}
