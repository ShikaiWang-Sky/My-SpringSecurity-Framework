package com.wang.spring_security_framework.config.SpringSecurityConfig;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Component
public class LogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HashMap<String, String> result = new HashMap<>();
        String returnUrl = "/toLoginPage";
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        result.put("code", "203");
        result.put("msg", "退出成功!");
        result.put("url", returnUrl);
        PrintWriter writer = response.getWriter();
        writer.write(JSON.toJSONString(result));
    }
}
