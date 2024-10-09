package com.itheima.reggie.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    private PasswordUtil(){
        throw new IllegalStateException("Utility class");
    }
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 密码比对
     * @param rawPassword 原始密码
     * @param encodedPassword 加密密码
     * @return
     */
    public static boolean matches(String rawPassword, String encodedPassword){
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 密码加密
     * @param rawPassword 原始密码
     * @return
     */
    public static String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
