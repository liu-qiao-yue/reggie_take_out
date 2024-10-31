package com.itheima.reggie.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.EmployeeDto;
import com.itheima.reggie.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {

    Employee login(HttpServletRequest request, Employee employee);

    String logout(HttpServletRequest request);

    Page<Employee> employeePageList(Integer page, Integer pageSize, String name);

    boolean addEmployee(Employee employee);

    String updateEmployee(Employee employee);

    Boolean changePassword(EmployeeDto employeeDto);

    Boolean validate(Employee employee);
}
