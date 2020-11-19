package com.wang.spring_security_framework.config;

import com.wang.spring_security_framework.config.SpringSecurityConfig.LoginSuccessHandler;
import com.wang.spring_security_framework.service.UserService;
import com.wang.spring_security_framework.service.serviceImpl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//SpringSecurity设置
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //指定自定义的登录页面, 表单提交的url, 以及成功后的处理器
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/toLoginPage")
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureForwardUrl("/index")
                .and()
                .csrf()
                .disable();
//        .failureForwardUrl();
        //注销


        //允许iframe
//        http.headers().frameOptions().sameOrigin();

        //默认开启了csrf, 会拦截所有的POST请求, 我这里简单的将其关闭了!

    }

    //密码使用盐值加密 BCryptPasswordEncoder
    //BCrypt.hashpw() ==> 加密
    //BCrypt.checkpw() ==> 密码比较
    //我们在数据库中存储的都是加密后的密码, 只有在网页上输入时是明文的
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
