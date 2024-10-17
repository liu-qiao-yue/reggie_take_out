package com.itheima.reggie.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 配置类，用于配置Web应用程序的资源路径映射、消息转换器和ObjectMapper。
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 此方法用于配置静态资源路径映射。
     * 配置静态资源路径映射，包括后端和前端的资源路径。
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("WebMvcConfig is working");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }

    /**
     * 此方法用于扩展Spring MVC的消息转换器列表。
     * 它会创建一个新的MappingJackson2HttpMessageConverter实例，并对其进行配置，
     * 使得所有Long类型的字段在序列化时都被转换为字符串形式，以解决前端（如JavaScript）显示大整数时的精度问题。
     *
     * @param converters Spring MVC的消息转换器列表
     */

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建一个新的MappingJackson2HttpMessageConverter实例
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();

        // 获取当前converter使用的ObjectMapper实例
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        // 创建一个新的SimpleModule模块
        SimpleModule simpleModule = new SimpleModule();

        // 将Long类型的数据序列化为字符串，以解决JavaScript端显示大整数时的精度问题
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);

        // 同样处理原始类型long，因为Java中的long是一个primitive type，而Long是一个包装类
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // 注册上面创建的SimpleModule模块到ObjectMapper中
        objectMapper.registerModule(simpleModule);

        // 设置当前配置好的ObjectMapper回到converter中
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);

        // 将配置好的converter添加到converters列表的最前面，确保其优先级最高
        converters.add(0, jackson2HttpMessageConverter);
    }

    /**
     * 此方法用于创建并返回一个配置好的ObjectMapper实例。
     * 它同样会配置一个SimpleModule模块，使得所有Long类型的字段在序列化时都被转换为字符串形式。
     * 这个方法通常作为一个Bean注入到Spring容器中，以便在整个应用程序中共享相同的ObjectMapper配置。
     *
     * @param builder 用于构建ObjectMapper的Jackson2ObjectMapperBuilder
     * @return 配置好的ObjectMapper实例
     */
    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        // 使用提供的builder创建一个ObjectMapper实例，并指定不使用XML映射
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();

        // 创建一个新的SimpleModule模块
        SimpleModule simpleModule = new SimpleModule();

        // 直接将所有的Long类型转换为String，以防止在前端展示时出现精度损失
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);

        // 注册上面创建的SimpleModule模块到ObjectMapper中
        objectMapper.registerModule(simpleModule);

        // 返回配置好的ObjectMapper实例
        return objectMapper;
    }
}
