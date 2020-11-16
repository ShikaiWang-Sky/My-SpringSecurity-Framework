package com.wang.spring_security_framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    //密码使用盐值加密 BCryptPasswordEncoder
    //BCrypt.hashpw() ==> 加密
    //BCrypt.checkpw() ==> 密码比较
    //我们在数据库中存储的都是加密后的密码, 只有在网页上输入时是明文的
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
