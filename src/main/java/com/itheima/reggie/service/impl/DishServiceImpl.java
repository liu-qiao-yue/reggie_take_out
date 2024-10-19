package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.mapper.DishMapper;
import com.itheima.reggie.service.DishService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Override
    public int getCountByCategoryId(Long categoryId) {
        LambdaQueryWrapper<Dish> dishWrapper = Wrappers.lambdaQuery();
        dishWrapper.eq(Dish::getCategoryId, categoryId);
        return this.count(dishWrapper);
    }

    /**
     * 菜品分页查询
     * @param page 第几页
     * @param pageSize 每页显示条数
     * @param name 菜品名称
     * @return
     */
    @Override
    public R<Object> dishPageList(Integer page, Integer pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Dish> dishWrapper = Wrappers.lambdaQuery();
        dishWrapper.like(name != null, Dish::getName, name);
        dishWrapper.orderByDesc(Dish::getUpdateTime);
        return R.success(this.baseMapper.selectPage(dishPage, dishWrapper));
    }

    /**
     * 批量删除/批量启用/删除/启用
     * @param status 状态
     * @param ids 菜品id，可用,分割
     * @return
     */
    @Override
    public R<Object> updateDishStatus(Integer status, String ids) {
        String[] idsArr = ids.split(",");
        List<Dish> dishes = new ArrayList<>();
        Arrays.stream(idsArr).forEach(id ->
                dishes.add(Dish.builder().id(Long.parseLong(id)).status(status).build())
        );
        return R.success(this.updateBatchById(dishes));

    }
}
