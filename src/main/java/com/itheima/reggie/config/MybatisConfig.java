package com.itheima.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解决mybatis-plus SelectPage 没有total的问题
 */
@Configuration
public class MybatisConfig {
    @Bean
    @Deprecated
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
