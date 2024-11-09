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
    Page<DishDto> dishPageList(Integer page, Integer pageSize, String name);

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
    boolean saveWithFlavor(com.itheima.reggie.dto.DishDto dto);

    /**
     * 根据ID查询菜品
     * @param id
     * @return
     */
    DishDto getDishByIdWithFlavor(String id);
}
