package com.itheima.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.itheima.reggie.annotation.MaskInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

import static com.itheima.reggie.annotation.MaskInfo.MaskType.PARTIAL;

/**
 * 员工实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    @MaskInfo
    private String password;

    @MaskInfo(type=PARTIAL)
    private String phone;

    private String sex;

    @MaskInfo(type=PARTIAL)
    private String idNumber;//身份证

    private Integer status;

    @TableField(fill = FieldFill.INSERT)// 公共字段自动填充
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

}
