package com.itheima.reggie.service.impl;

import com.itheima.reggie.mapper.EmployeeMapper;
import com.itheima.reggie.service.UpdatePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class UpdatePasswordServiceImpl implements UpdatePasswordService {

    @Autowired
    protected EmployeeMapper employeeMapper;
}
