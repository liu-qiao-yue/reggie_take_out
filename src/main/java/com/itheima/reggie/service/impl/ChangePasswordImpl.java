package com.itheima.reggie.service.impl;

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
public class ChangePasswordImpl extends AbstractUpdatePasswordServiceImpl{
    @Override
    public boolean validate(String type) {
        return StringUtils.equals(type, "change");
    }

    @Override
    public boolean process(EmployeeDto employeeDto) {

        Employee emp = super.employeeMapper.selectById(employeeDto.getId());
        //用户不存在
        if (emp == null) {
            throw new BizException(BizExceptionEnum.USERNAME_ERROR);
        }

        if (!PasswordUtil.matches(employeeDto.getOldPassword(), emp.getPassword())) {
            throw new BizException(BizExceptionEnum.PASSWORD_ERROR);
        }

        emp.setPassword(PasswordUtil.encodePassword(employeeDto.getPassword()));
        return super.employeeMapper.updateById(emp) > 0;
    }
}
