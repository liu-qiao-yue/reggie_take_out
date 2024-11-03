package com.itheima.reggie.handler;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取当前登录用户id
 * 每一个请求都是独立的一个线程，所以每个线程都有一个自己的threadLocal对象
 * @author ellie
 */
public class RequestContextHolder {

    private RequestContextHolder(){}

    private static final ThreadLocal<Long> THREAD_LOCAL = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id) {
        THREAD_LOCAL.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId() {
        return THREAD_LOCAL.get();
    }

    /**
     * 移除值
     */
    public static void unload() {
        THREAD_LOCAL.remove(); // Compliant
    }
}
