package com.itheima.reggie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AOP 配置，用于启用 AOP 功能，并且可以扫描到切面类
 * @author ellie
 */
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
    // 其他配置...
}
