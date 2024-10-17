package com.itheima.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 解决mybatis-plus SelectPage 没有total的问题
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 这两种方式都可以
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
//        public PaginationInterceptor paginationInterceptor() {
//            return new PaginationInterceptor();
//        }
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}