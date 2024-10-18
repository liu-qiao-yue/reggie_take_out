package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Override
    public int getCountByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> dishWrapper = Wrappers.lambdaQuery();
        dishWrapper.eq(Dish::getCategoryId, categoryId);
        return this.count(dishWrapper);
    }
}
