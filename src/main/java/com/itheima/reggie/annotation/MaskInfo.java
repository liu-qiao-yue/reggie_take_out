package com.itheima.reggie.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要掩码处理的字段。
 * @author ellie
 */
@SuppressWarnings("ALL")
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaskInfo {
    /**
     * 字段隐藏类型
     */
    enum MaskType {

        /**
         * 全隐藏，默认
         */
        FULL,
        /**
         * 部分隐藏
         */
        PARTIAL
    }

    MaskType type() default MaskType.FULL;
}