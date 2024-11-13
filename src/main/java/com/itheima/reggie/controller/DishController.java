package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.entity.Dish;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author ellie
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    /**
     * 菜品分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> dishPageList(@RequestParam("page") Integer page,
                                @RequestParam("pageSize") Integer pageSize,
                                @RequestParam(value = "name", required = false) String name) {
        return R.success(dishService.dishPageList(page, pageSize, name));
    }

    /**
     * 获取菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Object> getDishById(@PathVariable("id") String id) {
        return R.success(dishService.getDishByIdWithFlavor(id));
    }

    /**
     * 删除菜品
     * @param ids 菜品ID，多个以逗号分隔
     * @return
     */
    @DeleteMapping
    public R<Object> deleteDish(@RequestParam("ids") String ids) {
        String[] idsArr = ids.split(",");
        return R.success(dishService.removeByIds(Arrays.asList(idsArr)));
    }

    /**
     * 批量删除/批量启用/删除/启用
     * @param status 状态
     * @param ids 菜品id，可用","分割
     * @return
     */
    @PostMapping("/status/{status}")
    public R<Object> updateDishStatus(@PathVariable("status") Integer status,
                                      @RequestParam("ids") String ids) {
        return R.success(dishService.updateDishStatus(status, ids));
    }



    /**
     * 新增保存菜品信息
     * @param dto 菜品&口味信息
     * @return
     */
    @PostMapping
    public R<Object> saveDish(@RequestBody DishDto dto) {
        return R.success(dishService.saveWithFlavor(dto));
    }


    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        return R.success(dishService.getCategoryList(dish));
    }

}
