package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.SetmealDto;
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
    Page<SetmealDto> setmealPageList(Integer page, Integer pageSize, String name);


    /**
     * 更新套餐状态
     * @param status
     * @param ids
     * @return
     */
    boolean updateStatus(Integer status, String ids);

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    boolean saveWithDishes(SetmealDto setmealDto);

    /**
     * 根据套餐ID获取套餐信息
     * @param id
     * @return
     */
    SetmealDto getSetmealByIdWithDishes(String id);
}
