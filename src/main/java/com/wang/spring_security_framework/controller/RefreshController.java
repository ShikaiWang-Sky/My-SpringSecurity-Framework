package com.wang.spring_security_framework.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

//用于刷新
@RestController
public class RefreshController {
    //刷新token ==> 如果JWT过期
    @RequestMapping("/refreshJWT")
    public String refreshJWT(HttpServletRequest request) {
        return JSON.toJSONString(request.getAttribute("auth"));
    }
}
