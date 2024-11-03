package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Setmeal;

/**
 * @author ellie
 */
public interface SetmealService extends IService<Setmeal> {

    /**
     * 通过欸行ID获取套餐数量
     * @param categoryId
     * @return
     */
    int getCountByCategoryId(Long categoryId);

    /**
     * 套餐分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Setmeal> setmealPageList(Integer page, Integer pageSize, String name);
}
