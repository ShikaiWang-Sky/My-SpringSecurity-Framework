package com.wang.spring_security_framework.dao;

import com.wang.spring_security_framework.entity.DTO.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    //根据用户id查询用户
    UserDTO getUserById(String id);

    //根据用户名查询用户
    UserDTO getUserByUserName(String username);

    //添加用户
    Integer addUser(UserDTO user);


}
