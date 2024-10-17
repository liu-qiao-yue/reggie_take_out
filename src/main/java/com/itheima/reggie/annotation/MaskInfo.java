package com.itheima.reggie.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要掩码处理的字段。
 */
@Target(ElementType.FIELD) // 注解作用在字段上
@Retention(RetentionPolicy.RUNTIME) // 注解保留在运行时
public @interface MaskInfo {
    // 可以在这里定义注解的属性，如果有需要的话
}