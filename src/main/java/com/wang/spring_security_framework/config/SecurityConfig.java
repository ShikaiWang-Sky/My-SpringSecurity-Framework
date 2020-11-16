package com.wang.spring_security_framework.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//SpringSecurity设置
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //为 admin 角色分配权限页面
//        http.authorizeRequests().antMatchers("/").permitAll()
//                .antMatchers("/admin/**").hasRole("admin");
//                .antMatchers("/user/**").hasRole("user");

        //没有权限, 则返回登录页面
        http.formLogin();
//        .loginPage()
//        .loginProcessingUrl()
//        .failureForwardUrl();
        //注销

        //允许iframe
        http.headers().frameOptions().sameOrigin();

        //默认开启了csrf, 会拦截所有的POST请求, 我这里简单的将其关闭了!
        http.csrf().disable();

    }
}
