package com.wang.spring_security_framework.config.SpringSecurityConfig;

import com.wang.spring_security_framework.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

//登录成功处理, 用于比对验证码
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    CaptchaService captchaService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("success!");
        System.out.println(request.getParameter("token"));
        //校验验证码
        Boolean verifyResult = captchaService.versifyCaptcha(request.getParameter("token"),
                request.getParameter("inputCode"));
        System.out.println(verifyResult);
        if (verifyResult) {
            response.setContentType("application/json; charset=UTF-8");
            PrintWriter writer = response.getWriter();
            HashMap<String, String> map = new HashMap<>();
            map.put("url", "/toRegisterPage");
            System.out.println(map);
            response.sendRedirect("/toRegisterPage");
        } else {
            response.sendRedirect("/toRegisterPage");
        }
    }
}
