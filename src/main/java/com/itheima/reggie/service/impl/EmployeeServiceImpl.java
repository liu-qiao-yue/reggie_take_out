package com.itheima.reggie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.PasswordUtil;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.enums.BizExceptionEnum;
import com.itheima.reggie.exception.BizException;
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
            throw new BizException(BizExceptionEnum.LOGIN_ERROR);

        //密码错误
        if (!PasswordUtil.matches(employee.getPassword(), emp.getPassword()))
            throw new BizException(BizExceptionEnum.LOGIN_ERROR);
        //是否被禁用
        if (emp.getStatus() == 0)
            throw new BizException(BizExceptionEnum.ACCOUNT_FORBIDDEN);

        //存入session
        request.getSession().setAttribute(SESSION_KEY, emp);
        return R.success(emp);
    }

    /**
     * 退出登录 -> 清除session
     * @param request
     * @return
     */
    @Override
    public R<Object> logout(HttpServletRequest request) {
        //清除session
        request.getSession().removeAttribute(SESSION_KEY);
        return R.success("Logged out.");
    }

    /**
     * 分页查询员工信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public R<Object> employeePageList(Integer page, Integer pageSize, String name) {
        Page<Employee> employeePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(name))
            wrapper.like(Employee::getName, name);
        this.baseMapper.selectPage(employeePage, wrapper);
        return R.success(employeePage);
    }

    /**
     * 新增员工
     * @param request
     * @param employee
     * @return
     */
    @Override
    public R<Object> addEmployee(HttpServletRequest request, Employee employee) {
        //保证userName唯一
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        if (this.count(wrapper) > 0)
            throw new BizException(BizExceptionEnum.USERNAME_IS_EXIST);

        //获取当前登录用户ID
        Long userId = ((Employee) request.getSession().getAttribute(SESSION_KEY)).getId();
        employee.setPassword(PasswordUtil.encodePassword("123456"));
        employee.setCreateUser(userId);
        employee.setUpdateUser(userId);
        return R.success(this.save(employee));
    }

    /**
     * 编辑员工信息
     * @param id
     * @return
     */
    @Override
    public R<Object> editEmployee(String id) {
        Employee employee = this.getById(id);
        if (employee == null)
            throw new BizException(BizExceptionEnum.EMPLOYEE_NOT_FIND);

        employee.setPassword(null);
        return R.success(employee);
    }

    /**
     * 更新员工信息
     * @param request
     * @param employee
     * @return
     */
    @Override
    public R<Object> updateEmployee(HttpServletRequest request, Employee employee) {
        LambdaUpdateWrapper<Employee> updateWrapper = Wrappers.lambdaUpdate();
        if (StringUtils.isNotBlank(employee.getIdNumber())) {//编辑更新
            updateWrapper
                    //用户账号
                    .set(Employee::getUsername, employee.getUsername())
                    //姓名
                    .set(Employee::getName, employee.getName())
                    //手机号
                    .set(Employee::getPhone, employee.getPhone())
                    //性别
                    .set(Employee::getSex, employee.getSex());
            if (!employee.getIdNumber().contains("*"))
                //身份证
                updateWrapper.set(Employee::getIdNumber, employee.getIdNumber());
        } else {//启用或禁用
            updateWrapper.set(Employee::getStatus, employee.getStatus());
        }
        //更新时间
        updateWrapper.set(Employee::getUpdateUser, ((Employee) request.getSession().getAttribute(SESSION_KEY)).getId());
        updateWrapper.eq(Employee::getId, employee.getId());
        if (!this.update(updateWrapper))
            throw new BizException(BizExceptionEnum.UPDATE_ERROR);
        return R.success("update success.");
    }
}
