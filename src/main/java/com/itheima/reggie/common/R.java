package com.itheima.reggie.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果，服务段响应的数据最终都会封装成此对象
 * @param <T>
 */
@Data
public class R<T> {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //NOSONAR

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>(); //NOSONAR
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {//NOSONAR
        R r = new R();//NOSONAR
        r.msg = msg;
        r.code = 0;
        return r;//NOSONAR
    }

    public static <T> R<T> error(Integer code, String msg) { //NOSONAR
        R r = new R();//NOSONAR
        r.msg = msg;
        r.code = code;
        return r;//NOSONAR
    }

    public static <T> R<T> error(Integer code, String msg, Map map) { //NOSONAR
        R r = new R(); //NOSONAR
        r.msg = msg;
        r.code = code;
        r.map = map;
        return r; //NOSONAR
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
