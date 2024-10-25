package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public R<Object> setmealPageList(@RequestParam("page") Integer page,
                                  @RequestParam("pageSize") Integer pageSize,
                                  @RequestParam(value = "name", required = false)String name) {
        return R.success(setmealService.setmealPageList(page, pageSize, name));
    }
}
