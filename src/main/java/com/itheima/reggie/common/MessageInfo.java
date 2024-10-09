package com.itheima.reggie.common;

public abstract class MessageInfo {

    private MessageInfo(){
        throw new IllegalStateException("Utility class");
    }

    public static final String NOT_EXIST = "The account is not found.";

    public static final String PASSWORD_IS_ERROR = "The password is wrong.";

    public static final String ACCOUNT_FORBIDDEN = "The account has been banned from use.";

    public static final String LOGOUT_SUCCESS = "Logged out.";

    public static final String EMPLOYEE_NOT_FIND = "The employee does not find.";

    public static final String UPDATE_SUCCESS = "update success.";

    public static final String UPDATE_ERROR = "update error.";

}


