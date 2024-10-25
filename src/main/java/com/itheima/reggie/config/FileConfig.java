package com.itheima.reggie.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileConfig {

    private FileConfig() {

    }

    @Value("${file.basePath}")
    private String basePath;

    public String getBasePath() {
        return basePath;
    }
}