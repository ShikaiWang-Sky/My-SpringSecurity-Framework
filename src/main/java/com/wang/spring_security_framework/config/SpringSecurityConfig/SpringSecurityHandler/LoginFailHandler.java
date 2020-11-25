package com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityHandler;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

//认证失败的处理器
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HashMap<String, String> result = new HashMap<>();
        String AuthenticationFailUrl = "/toRegisterPage";
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        result.put("code", "202");
        result.put("msg", "认证失败!密码或用户名错误!即将跳转到注册页面!");
        result.put("url", AuthenticationFailUrl);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
    }
}
