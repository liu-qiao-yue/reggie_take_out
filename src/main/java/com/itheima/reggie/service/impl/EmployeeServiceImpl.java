package com.itheima.reggie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.MessageInfo;
import com.itheima.reggie.common.PasswordUtil;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    private static final String SESSION_KEY = "employee";

    /**
     * 登录逻辑: 根据前端输入用户名 -> 查询数据库该用户是否存在 -> 比对密码 -> 查看员工状态是否被禁用 -> 将用户id存入session
     *
     * @param request  登录成功后存放员工信息
     * @param employee 用户名&密码
     * @return
     */
    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = this.getOne(wrapper);
        //用户不存在
        if (emp == null)
            return R.error(MessageInfo.NOT_EXIST);

        //密码错误
        if (!PasswordUtil.matches(employee.getPassword(), emp.getPassword()))
            return R.error(MessageInfo.PASSWORD_IS_ERROR);
        //是否被禁用
        if (emp.getStatus() == 0)
            return R.error(MessageInfo.ACCOUNT_FORBIDDEN);

        //存入session
        request.getSession().setAttribute(SESSION_KEY, emp);
        return R.success(emp);
    }

    @Override
    public R<Object> logout(HttpServletRequest request) {
        //清除session
        request.getSession().removeAttribute(SESSION_KEY);
        return R.success(MessageInfo.LOGOUT_SUCCESS);
    }

    @Override
    public R<Object> employeePageList(Integer page, Integer pageSize, String name) {
        Page<Employee> employeePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(name))
            wrapper.like(Employee::getName, name);
        this.baseMapper.selectPage(employeePage, wrapper);
        return R.success(employeePage);
    }

    @Override
    public R<Object> addEmployee(HttpServletRequest request, Employee employee) {
        employee.setPassword(PasswordUtil.encodePassword("123456"));
        Long userId = ((Employee) request.getSession().getAttribute(SESSION_KEY)).getId();
        employee.setCreateUser(userId);
        employee.setUpdateUser(userId);
        return R.success(this.save(employee));
    }

    @Override
    public R<Object> editEmployee(String id) {
        Employee employee = this.getById(id);
        return employee == null ? R.error(MessageInfo.EMPLOYEE_NOT_FIND) : R.success(employee);
    }

    @Override
    public R<Object> updateEmployee(Employee employee) {
        LambdaUpdateWrapper<Employee> updateWrapper = Wrappers.lambdaUpdate();
        if (StringUtils.isNotBlank(employee.getIdNumber())) {//编辑更新
            updateWrapper.set(Employee::getUsername, employee.getUsername())
                    .set(Employee::getName, employee.getName())
                    .set(Employee::getPhone, employee.getPhone())
                    .set(Employee::getSex, employee.getSex());
            if (!employee.getIdNumber().contains("*"))
                updateWrapper.set(Employee::getIdNumber, employee.getIdNumber());
        } else {//启用或禁用
            updateWrapper.set(Employee::getStatus, employee.getStatus());
        }
        updateWrapper.eq(Employee::getId, employee.getId());
        return this.update(updateWrapper) ?  R.success(MessageInfo.UPDATE_SUCCESS) : R.error(MessageInfo.UPDATE_ERROR);
    }
}
