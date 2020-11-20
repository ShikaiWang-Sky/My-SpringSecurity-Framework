package com.wang.spring_security_framework.service.serviceImpl;

import com.wang.spring_security_framework.entity.DTO.UserDTO;
import com.wang.spring_security_framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("username = " + username);
        UserDTO user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return User.withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities("p1")
                    .build();
        }
    }
}
