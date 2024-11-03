package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;

/**
 * @author ellie
 */
public interface DishService extends IService<Dish> {
    /**
     * 通过类型ID获取菜品数量
     * @param categoryId
     * @return
     */
    int getCountByCategoryId(Long categoryId);

    /**
     * 菜品分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Dish> dishPageList(Integer page, Integer pageSize, String name);

    /**
     * 更新菜品状态
     * @param status
     * @param ids
     * @return
     */
    boolean updateDishStatus(Integer status, String ids);

    /**
     * 新增菜品
     * @param dto
     * @return
     */
    boolean saveDishInfos(DishDto dto);
}
