package com.wang.spring_security_framework.config;

import com.wang.spring_security_framework.config.SpringSecurityConfig.LoginFailHandler;
import com.wang.spring_security_framework.config.SpringSecurityConfig.LoginSuccessHandler;
import com.wang.spring_security_framework.config.SpringSecurityConfig.MyCustomAuthenticationFilter;
import com.wang.spring_security_framework.service.UserService;
import com.wang.spring_security_framework.service.serviceImpl.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//SpringSecurity设置
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    LoginSuccessHandler loginSuccessHandler;
    @Autowired
    LoginFailHandler loginFailHandler;

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //指定自定义的登录页面, 表单提交的url, 以及成功后的处理器
        http.formLogin()
                .loginPage("/toLoginPage")
                .and().csrf().disable();
        //注销

        //设置过滤器链, 添加自定义过滤器
        http.addFilterAt(
                myCustomAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class
        );
        //允许iframe
//        http.headers().frameOptions().sameOrigin();
    }

    //注册自定义过滤器
    @Bean
    MyCustomAuthenticationFilter myCustomAuthenticationFilter() throws Exception {

        MyCustomAuthenticationFilter filter = new MyCustomAuthenticationFilter();

        //设置过滤器认证管理
        filter.setAuthenticationManager(super.authenticationManagerBean());
        //设置filter的url
        filter.setFilterProcessesUrl("/login");
        //设置登录成功处理器
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        //设置登录失败处理器
        filter.setAuthenticationFailureHandler(loginFailHandler);

        return filter;
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
