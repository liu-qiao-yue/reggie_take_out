package com.itheima.reggie.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {

    R<Employee> login(HttpServletRequest request, Employee employee);

    R<Object> logout(HttpServletRequest request);

    R<Object> employeePageList(Integer page, Integer pageSize, String name);

    R<Object> addEmployee(Employee employee);

    R<Object> updateEmployee(Employee employee);
}
