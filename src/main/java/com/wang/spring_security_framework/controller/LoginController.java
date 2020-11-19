package com.wang.spring_security_framework.controller;

import com.wang.spring_security_framework.entity.DTO.UserDTO;
import com.wang.spring_security_framework.service.CaptchaService;
import com.wang.spring_security_framework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    CaptchaService captchaService;
    @Autowired
    UserService userService;

    @PostMapping("/captcha")
    public Map<String, Object> captcha() throws IOException {
        return captchaService.captchaCreator();
    }

    @GetMapping("/register")
    public Integer register(UserDTO user) {
        return userService.addUser(user);
    }
}
