package com.itheima.reggie.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.common.MessageInfo;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    /**
     * 登录逻辑: 根据前端输入用户名 -> 查询数据库该用户是否存在 -> 比对密码 -> 查看员工状态是否被禁用 -> 将用户id存入session
     * @param request 登录成功后存放员工信息
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
        PasswordEncoder passwordEncoder = new StandardPasswordEncoder();
        if (!passwordEncoder.matches(employee.getPassword(), emp.getPassword()))
            return R.error(MessageInfo.PASSWORD_IS_ERROR);
        //是否被禁用
        if (emp.getStatus() == 0)
            return R.error(MessageInfo.ACCOUNT_FORBIDDEN);

        //存入session
        request.getSession().setAttribute("employee", emp);
        return R.success(emp);
    }

    @Override
    public R<Object> logout(HttpServletRequest request) {
        //清除session
        request.getSession().removeAttribute("employee");
        return R.success(MessageInfo.LOGOUT_SUCCESS);
    }

    @Override
    public R<Object> employeePageList() {
        return null;
    }
}
