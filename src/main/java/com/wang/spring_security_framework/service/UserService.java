package com.wang.spring_security_framework.service;

import com.wang.spring_security_framework.entity.DTO.PermissionDTO;
import com.wang.spring_security_framework.entity.DTO.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO getUserById (String id);

    UserDTO getUserByUsername(String username);

    Integer addUser(UserDTO user);

    List<PermissionDTO> getPermissionByUserId(String userId);
}
