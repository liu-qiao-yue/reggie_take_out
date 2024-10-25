package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.R;
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

    @Override
    public Page<Setmeal> setmealPageList(Integer page, Integer pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = Wrappers.lambdaQuery();
        wrapper.like(name != null, Setmeal::getName, name);
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        this.page(pageInfo, wrapper);
        return pageInfo;
    }
}
