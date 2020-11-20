package com.wang.spring_security_framework.config.SpringSecurityConfig;

import com.alibaba.fastjson.JSON;
import com.wang.spring_security_framework.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//登录成功处理
//我们不能在这里获得request了, 因为我们已经在前面自定义了认证过滤器, 做完后SpringSecurity会关闭inputStream流
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    CaptchaService captchaService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //我们从自定义的认证过滤器中拿到的authInfo, 接下来做验证码校验和跳转
        Map<String, String> authInfo = (Map<String, String>) request.getAttribute("authInfo");
        System.out.println(authInfo);
        System.out.println("success!");
        String token = authInfo.get("token");
        String inputCode = authInfo.get("inputCode");

        //校验验证码
        Boolean verifyResult = captchaService.versifyCaptcha(token, inputCode);
        System.out.println(verifyResult);

        Map<String, String> result = new HashMap<>();
        if (verifyResult) {
            HashMap<String, String> map = new HashMap<>();
            map.put("url", "/index");
            System.out.println(map);
            String VerifySuccessUrl = "/index";
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            result.put("code", "200");
            result.put("msg", "认证成功!");
            result.put("url", VerifySuccessUrl);
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(result));
        } else {
            String VerifyFailedUrl = "/toLoginPage";
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            result.put("code", "201");
            result.put("msg", "验证码输入错误!");
            result.put("url", VerifyFailedUrl);
            PrintWriter writer = response.getWriter();
            writer.write(JSON.toJSONString(result));
        }
    }
}
