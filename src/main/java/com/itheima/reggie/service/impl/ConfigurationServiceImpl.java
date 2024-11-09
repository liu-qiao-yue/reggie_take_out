package com.itheima.reggie.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.itheima.reggie.entity.Configuration;
import com.itheima.reggie.mapper.ConfigurationMapper;
import com.itheima.reggie.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 口味配置
 * @author ellie
 */
@Service
public class ConfigurationServiceImpl extends MPJBaseServiceImpl<ConfigurationMapper, Configuration> implements ConfigurationService {

    @Autowired
    private final ConfigurationMapper configurationMapper;

    public ConfigurationServiceImpl(ConfigurationMapper configurationMapper) {
        this.configurationMapper = configurationMapper;
    }
    /**
     * 获取所有类别的树形结构
     * @return 树形结构的类别列表
     */
    @Override
    public List<Configuration> getCategoryTree(Configuration configuration) {
        return configurationMapper.selectConfigurationWithConfiguration(configuration);
    }

    @Override
    @Transactional
    public Boolean saveOrUpdateConfiguration(Configuration configuration) {
        this.saveOrUpdate(configuration);

        // insert children
        this.saveBatch(configuration.getChildren());

        return true;
    }
}
