package com.wang.spring_security_framework.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

public interface CaptchaService {
    //生成token
    Map<String, Object> createToken(String captcha);
    //生成captcha验证码
    Map<String, Object> captchaCreator() throws IOException;
    //验证输入的验证码是否正确
    String versifyCaptcha (String token, String inputCode);
}
