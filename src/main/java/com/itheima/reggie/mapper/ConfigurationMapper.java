package com.itheima.reggie.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.itheima.reggie.entity.Configuration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ellie
 */

@Mapper
public interface ConfigurationMapper extends MPJBaseMapper<Configuration> {
    List<Configuration> selectConfigurationWithConfiguration(@Param("configuration") Configuration configuration);
}
