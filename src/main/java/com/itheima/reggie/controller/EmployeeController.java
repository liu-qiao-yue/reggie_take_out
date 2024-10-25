package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.handler.RequestContextHolder;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 员工登录
     * @param request 登录成功后存放员工信息
     * @param employee 用户名&密码
     * @return
     */
    @PostMapping("/login")
    public R<Employee> employeeLogin(HttpServletRequest request,
                                     @RequestBody Employee employee){
        return R.success(employeeService.login(request, employee));
    }

    /**
     * 员工退出登录
     * @param request 删除session员工信息
     * @return
     */
    @PostMapping("/logout")
    public R<String> employeeLogout(HttpServletRequest request){
        return R.success(employeeService.logout(request));
    }

    /**
     * 修改密码 / 重置密码
     * @param passwordInfo 用户名&旧密码&新密码&修改类型[change|reset]
     * @return
     */
    @PostMapping("/changePassword")
    public R<Boolean> changePassword(@RequestBody Map<String, String> passwordInfo){
        return R.success(employeeService.changePassword(passwordInfo));
    }

    /**
     * 新增员工
     * @param employee 员工信息
     * @return
     */
    @PostMapping
    public R<Boolean> addEmployee(@RequestBody Employee employee){
        try {
            return R.success(employeeService.addEmployee(employee));
        }finally {
            RequestContextHolder.unload();
        }

    }

    /**
     * 员工页面list
     * @param page 第几页
     * @param pageSize 每页几行
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> employeePageList(@RequestParam("page") Integer page,
                                    @RequestParam("pageSize") Integer pageSize,
                                    @RequestParam(value = "name", required = false)String name){
        return R.success(employeeService.employeePageList(page, pageSize, name));
    }

    /**
     * 编辑员工信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public R<Employee> editEmployee(@PathVariable String id){
        return R.success(employeeService.getById(id));
    }

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> updateEmployee(@RequestBody Employee employee){
        try {
            return R.success(employeeService.updateEmployee(employee));
        }finally {
            RequestContextHolder.unload();
        }
    }
}
