package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Employee;
import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDto extends Employee  implements Serializable {
    private String oldPassword;
    private String type;// 1:修改密码 2:修改信息
}
