package com.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.reggie.entity.User;

public interface UserService extends IService<User> {
    /**
     * 获取收集验证码
     * @param user
     * @return
     */
    String sendMsg(User user);
}
