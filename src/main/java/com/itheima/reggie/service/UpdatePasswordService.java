package com.itheima.reggie.service;

import com.itheima.reggie.dto.EmployeeDto;

/**
 * @author ellie
 */
public interface UpdatePasswordService {
    /**
     * 验证使用实现类型
     * @param type
     * @return
     */
    boolean validate(String type);

    /**
     * 实现进程
     * @param employeeDto
     * @return
     */
    boolean process(EmployeeDto employeeDto);
}
