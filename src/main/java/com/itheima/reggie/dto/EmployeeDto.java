package com.itheima.reggie.dto;

import com.itheima.reggie.entity.Employee;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author ellie
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmployeeDto extends Employee  implements Serializable {
    private String oldPassword;
    /**
     * 1:修改密码 2:修改信息
     */
    private String type;
}
