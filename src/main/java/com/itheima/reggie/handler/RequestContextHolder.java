package com.itheima.reggie.handler;

/**
 * 基于ThreadLocal封装工具类，用于保存和获取当前登录用户id
 * 每一个请求都是独立的一个线程，所以每个线程都有一个自己的threadLocal对象
 */
public class RequestContextHolder {

    private RequestContextHolder(){}

    //创建一个ThreadLocal对象
    private static final ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }

    /**
     * 移除值
     */
    public static void unload() {
        threadLocal.remove(); // Compliant
    }
}
