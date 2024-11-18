package com.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.entity.User;
import com.itheima.reggie.mapper.UserMapper;
import com.itheima.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public static final String SESSION_USER_KEY = "user";

    /**
     * 获取收集验证码
     * @param user
     * @return
     */
    @Override
    public String sendMsg(User user) {// todo
        // 获取手机号

        // 生成验证码

        // 发送
        return null;
    }
}
