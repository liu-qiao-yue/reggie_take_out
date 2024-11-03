package com.itheima.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务段响应的数据最终都会封装成此对象
 * @author ellie
 * @param <T>
 */
@Data
public class R<T> {

    /**
     * 编码：1成功，0和其它数字为失败
     */
    private Integer code;

    private String msg;

    private T data;

    private Map map = new HashMap();

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public static <T> R<T> error(Integer code, String msg) {
        R r = new R();
        r.msg = msg;
        r.code = code;
        return r;
    }

    public static <T> R<T> error(Integer code, String msg, Map map) {
        R r = new R();
        r.msg = msg;
        r.code = code;
        r.map = map;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
