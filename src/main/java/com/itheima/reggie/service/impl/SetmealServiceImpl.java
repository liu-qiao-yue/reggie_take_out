package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.Setmeal;
import com.itheima.reggie.mapper.SetmealMapper;
import com.itheima.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Override
    public int getCountByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Setmeal> dishWrapper = Wrappers.lambdaQuery();
        dishWrapper.eq(Setmeal::getCategoryId, categoryId);
        return this.count(dishWrapper);
    }
}
