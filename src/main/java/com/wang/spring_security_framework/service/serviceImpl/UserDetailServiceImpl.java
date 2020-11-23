package com.wang.spring_security_framework.service.serviceImpl;

import com.wang.spring_security_framework.entity.DTO.PermissionDTO;
import com.wang.spring_security_framework.entity.DTO.UserDTO;
import com.wang.spring_security_framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.getUserByUsername(username);
        //查询用户权限
        List<PermissionDTO> permissions = userService.getPermissionByUserId(user.getId());
        //查询用户权限code
        List<String> permissionsCode = new ArrayList<>();
        for (PermissionDTO permission : permissions) {
            permissionsCode.add(permission.getCode());
        }

        System.out.println(permissionsCode.toString());

        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    //将授权信息放入userDetails中, 这里要求一个String数组
                    .authorities(permissionsCode.toArray(new String[permissionsCode.size()]))
                    .build();
        }
    }
}
