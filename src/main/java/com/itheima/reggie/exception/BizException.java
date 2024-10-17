package com.itheima.reggie.exception;

import com.itheima.reggie.enums.BizExceptionEnum;
import lombok.Data;

@Data
public class BizException extends RuntimeException {

    private final Integer code; //业务异常码
    private final String msg; //业务异常信息
    public BizException(Integer code, String message) {
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BizException(BizExceptionEnum exceptionEnum) {
        super(exceptionEnum.getMsg());
        this.code = exceptionEnum.getCode();
        this.msg = exceptionEnum.getMsg();
    }
}
