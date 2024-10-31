package com.itheima.reggie.service;

import com.itheima.reggie.dto.EmployeeDto;

public interface UpdatePasswordService {
    boolean validate(String type);
    boolean process(EmployeeDto employeeDto);
}
