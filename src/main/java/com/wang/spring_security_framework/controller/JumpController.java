package com.wang.spring_security_framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//处理页面跳转
@Controller
public class JumpController {

    @RequestMapping("/toRegisterPage")
    public String toRegisterPage() {
        return "login/register";
    }

    @RequestMapping("/toLoginPage")
    public String toLoginPage() {
        return "login/login";
    }

    @RequestMapping("/index")
    public String toIndexPage() {
        return "index";
    }

    @RequestMapping("/page")
    public String toContentPage() {
        return "content/layout";
    }

    @RequestMapping("/newPage")
    public String toNewPage() {
        return "content/page";
    }

}
