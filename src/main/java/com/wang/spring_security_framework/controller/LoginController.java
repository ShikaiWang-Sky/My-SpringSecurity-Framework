package com.wang.spring_security_framework.controller;

import com.wang.spring_security_framework.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    CaptchaService captchaService;

    @GetMapping("/captcha")
    public Map<String, Object> captcha() throws IOException {
        return captchaService.captchaCreator();
    }

    @GetMapping("/login1")
    public String login(@RequestParam("token") String token,
                              @RequestParam("inputCode") String inputCode) {
        return captchaService.versifyCaptcha(token, inputCode);
    }
}
