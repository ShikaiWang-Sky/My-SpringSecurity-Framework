package com.wang.spring_security_framework.service.serviceImpl;

import com.wang.spring_security_framework.dao.UserMapper;
import com.wang.spring_security_framework.entity.DTO.UserDTO;
import com.wang.spring_security_framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO getUserById(String id) {
        return userMapper.getUserById(id);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return userMapper.getUserByUserName(username);
    }

    @Override
    public Integer addUser(UserDTO user) {
        //先查看要添加的用户是否在数据库中
        String username = user.getUsername();
        UserDTO userByUsername = getUserByUsername(username);
        //如果待插入的用户存在在数据库中, 插入0条
        if (null != userByUsername) {
            return 0;
        } else {
            //不存在, 则插入用户
            //先对密码进行盐值加密, SpringSecurity中使用的是随机盐值加密
            String hashpw = passwordEncoder.encode(user.getPassword());
            user.setPassword(hashpw);
            return userMapper.addUser(user);
        }
    }

}
