package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.dto.DishDto;
import com.itheima.reggie.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
    public R<Object> dishPageList(@RequestParam("page") Integer page,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam(value = "name", required = false)String name) {//todo
        return R.success(dishService.dishPageList(page, pageSize, name));
    }

    /**
     * 获取菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Object> getDishById(@PathVariable("id") Long id) {//todo
        return R.success(dishService.getById(id));
    }

    /**
     * 删除菜品
     * @param ids 菜品ID，多个以逗号分隔
     * @return
     */
    @DeleteMapping
    public R<Object> deleteDish(@RequestParam("ids") String ids) {//todo
        String[] idsArr = ids.split(",");
        return R.success(dishService.removeByIds(Collections.singletonList(idsArr)));
    }

    /**
     * 批量删除/批量启用/删除/启用
     * @param status 状态
     * @param ids 菜品id，可用,分割
     * @return
     */
    @PostMapping("/status/{status}")
    public R<Object> updateDishStatus(@PathVariable("status") Integer status,
                                      @RequestParam("ids") String ids) {//todo
        return R.success(dishService.updateDishStatus(status, ids));
    }



    /**
     * [新增保存菜品信息]{
     *     "name": "我是菜品名称",
     *     "price": 999000,
     *     "code": "",
     *     "image": "b81c6947-9ac2-44e1-b481-e0ea5fa559da.png",
     *     "description": "我是添加菜品测试",
     *     "status": 1,
     *     "categoryId": "1847227623393320961",
     *     "flavors": [
     *         {
     *             "name": "甜味",
     *             "value": "[\"无糖\",\"少糖\",\"半糖\",\"多糖\",\"全糖\"]",
     *             "showOption": false
     *         }
     *     ]
     * }
     * @param dto 菜品&口味信息
     * @return
     */
    @PostMapping
    public R<Object> saveDish(@RequestBody DishDto dto) {//todo
        return R.success(dishService.saveDishInfos(dto));
    }

}
