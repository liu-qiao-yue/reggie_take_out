package com.itheima.reggie.controller;

import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request 登录成功后存放员工信息
     * @param employee 用户名&密码
     * @return
     */
    @PostMapping("/login")
    public R<Employee> employeeLogin(HttpServletRequest request,
                                     @RequestBody Employee employee){
        return employeeService.login(request, employee);
    }

    /**
     * 员工退出登录
     * @param request 删除session员工信息
     * @param employee 员工信息
     * @return
     */
    @PostMapping("/logout")
    public R<Object> employeeLogout(HttpServletRequest request){
        return employeeService.logout(request);
    }


    @GetMapping("/page")
    public R<Object> employeePageList(){
        return employeeService.employeePageList();
    }
}
