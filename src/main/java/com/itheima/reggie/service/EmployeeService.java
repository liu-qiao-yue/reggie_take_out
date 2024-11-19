package com.itheima.reggie.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.dto.EmployeeDto;
import com.itheima.reggie.entity.Employee;
import jakarta.servlet.http.HttpServletRequest;
/**
 * @author ellie
 */
public interface EmployeeService extends IService<Employee> {
    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    Employee login(HttpServletRequest request, Employee employee);

    /**
     * 退出
     * @param request
     * @return
     */
    String logout(HttpServletRequest request);

    /**
     * 员工分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    Page<Employee> employeePageList(Integer page, Integer pageSize, String name);

    /**
     * 新增员工
     * @param employee
     * @return
     */
    boolean addEmployee(Employee employee);

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    String updateEmployee(Employee employee);

    /**
     * 修改密码
     * @param employeeDto
     * @return
     */
    Boolean changePassword(EmployeeDto employeeDto);

    /**
     * 验证username是否唯一
     * @param employee
     * @return
     */
    Boolean validate(Employee employee);
}
