package com.itheima.reggie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.PasswordUtil;
import com.itheima.reggie.dto.EmployeeDto;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.enums.BizExceptionEnum;
import com.itheima.reggie.exception.BizException;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import com.itheima.reggie.service.UpdatePasswordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author ellie
 */
@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    public static final String SESSION_EMPLOYEE_KEY = "employee";

    /**
     *  策略模式：密码修改
     */
    @Autowired
    private List<UpdatePasswordService> passwordServices;

    /**
     * 登录逻辑: 根据前端输入用户名 -> 查询数据库该用户是否存在 -> 比对密码 -> 查看员工状态是否被禁用 -> 将用户id存入session
     *
     * @param request  登录成功后存放员工信息
     * @param employee 用户名&密码
     * @return R
     */
    @Override
    public Employee login(HttpServletRequest request, Employee employee) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = this.getOne(wrapper);
        //用户不存在
        if (emp == null) {
            throw new BizException(BizExceptionEnum.USERNAME_ERROR);
        }

        //密码错误
        if (!PasswordUtil.matches(employee.getPassword(), emp.getPassword())) {
            throw new BizException(BizExceptionEnum.PASSWORD_ERROR);
        }
        //是否被禁用
        if (emp.getStatus() == 0) {
            throw new BizException(BizExceptionEnum.ACCOUNT_FORBIDDEN);
        }

        //存入session
        request.getSession().setAttribute(SESSION_EMPLOYEE_KEY, emp.getId());
        return emp;
    }

    /**
     * 退出登录 -> 清除session
     *
     * @param request HttpServletRequest
     * @return R
     */
    @Override
    public String logout(HttpServletRequest request) {
        //清除session
        request.getSession().removeAttribute(SESSION_EMPLOYEE_KEY);
        return "Logged out.";
    }

    /**
     * 分页查询员工信息
     *
     * @param page     当前页
     * @param pageSize 每页多少行
     * @param name     名称
     * @return R
     */
    @Override
    public Page<Employee> employeePageList(Integer page, Integer pageSize, String name) {
        //分页构造器
        Page<Employee> employeePage = new Page<>(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
        //添加过滤条件
        wrapper.like(StringUtils.isNotBlank(name), Employee::getName, name);
        wrapper.orderByDesc(Employee::getUpdateTime);
        //执行查询
        this.baseMapper.selectPage(employeePage, wrapper);
        return employeePage;
    }

    /**
     * 新增员工
     *
     * @param employee 员工信息
     * @return R
     */
    @Override
    public boolean addEmployee(Employee employee) {
        //保证userName唯一
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        if (this.count(wrapper) > 0) {
            throw new BizException(BizExceptionEnum.USERNAME_IS_EXIST);
        }

        employee.setPassword(PasswordUtil.encodePassword(employee.getPassword()));

        return this.save(employee);
    }

    /**
     * 更新员工信息
     *
     * @param employee HttpServletRequest
     * @return R
     */
    @Override
    public String updateEmployee(Employee employee) {
        Employee.EmployeeBuilder em = Employee.builder()
                .id(employee.getId())
                .sex(employee.getSex())
                .name(employee.getName())
                .username(employee.getUsername())
                .phone(employee.getPhone())
                .status(employee.getStatus());

        //身份证
        em.idNumber(employee.getIdNumber());

        //updateById时
        if (!this.updateById(em.build())) {
            throw new BizException(BizExceptionEnum.UPDATE_ERROR);
        }
        return "update success.";
    }

    @Override
    public Boolean changePassword(EmployeeDto employeeDto) {
        return passwordServices
                .stream()
                .filter(service -> service.validate(employeeDto.getType()))
                .findFirst()
                .orElseThrow(() -> new BizException(BizExceptionEnum.INPUT_ERROR))
                .process(employeeDto);
    }

    @Override
    public Boolean validate(Employee employee) {
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        if (employee.getId() != null) {
            wrapper.ne(Employee::getId, employee.getId());
        }
        return this.count(wrapper) > 0;
    }
}
