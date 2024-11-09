package com.itheima.reggie.controller;


import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Configuration;
import com.itheima.reggie.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/flavor/configuration")
public class ConfigurationController {

    @Autowired
    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    /**
     * 口味做法配置树
     * @param configuration
     * @return
     */
    @PostMapping
    public R<List<Configuration>> getCategoryTree(@RequestBody Configuration configuration) {
        return R.success(configurationService.getCategoryTree(configuration));
    }

    @PostMapping("save")
    public R<Boolean> saveConfiguration(@RequestBody Configuration configuration) {
        return R.success(configurationService.saveOrUpdateConfiguration(configuration));
    }

}
