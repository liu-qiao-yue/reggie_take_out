package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.itheima.reggie.common.PasswordUtil;
import com.itheima.reggie.dto.EmployeeDto;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.enums.BizExceptionEnum;
import com.itheima.reggie.exception.BizException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author ellie
 */
@Component
public class ForgotPasswordImpl extends AbstractUpdatePasswordServiceImpl {
    @Override
    public boolean validate(String type) {
        return StringUtils.equals(type, "forgot");
    }

    @Override
    public boolean process(EmployeeDto employeeDto) {
        LambdaQueryWrapper<Employee> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Employee::getName, employeeDto.getName());
        wrapper.eq(Employee::getUsername, employeeDto.getUsername());
        wrapper.eq(Employee::getIdNumber, employeeDto.getIdNumber());

        Integer count = super.employeeMapper.selectCount(wrapper);

        if (count != 1) {
            throw new BizException(BizExceptionEnum.USERNAME_ERROR);
        }

        Employee employee = Employee
                .builder()
                .password(PasswordUtil.encodePassword(employeeDto.getPassword()))
                .build();
        return super.employeeMapper.update(employee, wrapper) == 1;
    }
}
