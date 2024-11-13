package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.SetmealDto;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ellie
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private final SetmealService setmealService;

    @Autowired
    public SetmealController(SetmealService setmealService) {
        this.setmealService = setmealService;
    }

    /**
     * 菜品分页查询
     * @param page 第几页
     * @param pageSize 每页几条
     * @param name 套餐名称
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> setmealPageList(@RequestParam("page") Integer page,
                                              @RequestParam("pageSize") Integer pageSize,
                                              @RequestParam(value = "name", required = false)String name) {
        return R.success(setmealService.setmealPageList(page, pageSize, name));
    }


    /**
     * 批量删除/批量启用/删除/启用
     * @param status 状态
     * @param ids 套餐id，可用","分割
     * @return
     */
    @PostMapping("/status/{status}")
    public R<Object> updateStatus(@PathVariable("status") Integer status,
                                      @RequestParam("ids") String ids) {
        return R.success(setmealService.updateStatus(status, ids));
    }

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<Boolean> saveWithDishes(@RequestBody SetmealDto setmealDto) {
        return R.success(setmealService.saveWithDishes(setmealDto));
    }

    /**
     * 根据套餐id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealByIdWithDishes(@PathVariable("id") String id) {
        return R.success(setmealService.getSetmealByIdWithDishes(id));
    }
}
