package com.itheima.reggie.enums;

import lombok.Getter;

@Getter
public enum BizExceptionEnum {

    //登录登出等模块
    LOGIN_ERROR(100001, "The userName or password is wrong."),
    ACCOUNT_FORBIDDEN(100002, "The account has been banned from use."),
    //通用模块
    UPDATE_ERROR(100003, "update error."),
    //员工模块
    USERNAME_IS_EXIST(210001, "The username is exist."),
    EMPLOYEE_NOT_FIND(210002, "The employee does not find."),

    CATEGORY_IS_EXIST(220001, "The category is exist."),
    CATEGORY_IS_RELATED(220002, "This category has related dishes or set menus and cannot be deleted");



    private final Integer code;
    private final String msg;


    private BizExceptionEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
