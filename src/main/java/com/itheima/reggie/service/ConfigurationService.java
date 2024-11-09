package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.Configuration;

import java.util.List;

/**
 * 口味做法配置表
 * @author ellie
 */
public interface ConfigurationService extends IService<Configuration> {

    List<Configuration> getCategoryTree(Configuration configuration);

    Boolean saveOrUpdateConfiguration(Configuration configuration);
}
