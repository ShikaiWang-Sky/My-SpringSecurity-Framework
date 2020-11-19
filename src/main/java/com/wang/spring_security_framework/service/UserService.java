package com.wang.spring_security_framework.service;

import com.wang.spring_security_framework.entity.DTO.UserDTO;

public interface UserService {

    UserDTO getUserById (String id);

    UserDTO getUserByUsername(String username);

    Integer addUser(UserDTO user);
}
