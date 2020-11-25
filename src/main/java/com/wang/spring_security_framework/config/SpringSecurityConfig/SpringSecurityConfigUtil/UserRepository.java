package com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityConfigUtil;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 存入user token,可以引用缓存系统，存入到缓存。
 */
@Component
public class UserRepository {

    private static final Map<String, User> userMap = new HashMap<>();

    public User findByUsername(final String username) {
        return userMap.get(username);
    }

    public User insert(User user) {
        userMap.put(user.getUsername(), user);
        return user;
    }

    public void remove(String username) {
        userMap.remove(username);
    }
}