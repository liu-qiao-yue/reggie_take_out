package com.itheima.reggie.exception;

/**
 * 自定义异常用于拦截敏感信息
 */
public class MaskException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MaskException(String message) {
        super(message);
    }
}
