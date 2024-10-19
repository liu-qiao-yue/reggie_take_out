package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Dish;

public interface DishService extends IService<Dish> {
    int getCountByCategoryId(Long categoryId);

    R<Object> dishPageList(Integer page, Integer pageSize, String name);

    R<Object> updateDishStatus(Integer status, String ids);
}
