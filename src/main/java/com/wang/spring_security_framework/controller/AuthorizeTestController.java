package com.wang.spring_security_framework.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/r")
public class AuthorizeTestController {

    //从会话中获取当前登录用户名
    private String getUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //未登录, 返回null
        if(!authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    //获取当前的授权信息
    private String getAuthorization() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //未登录, 返回null
        if(!authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getAuthorities();
        return principal.toString();
    }

    @RequestMapping("/r1")
    public String R1() {
        return JSON.toJSONString(getUserName() + "访问资源1");
    }

    @RequestMapping("/r2")
    public String R2() {
        return getUserName() + "访问资源2";
    }

    @RequestMapping("/r3")
    public String R3() {
        return getUserName() + "访问资源3";
    }

    @RequestMapping("/username")
    public String userName() {
        return JSON.toJSONString(getUserName());
    }
}
