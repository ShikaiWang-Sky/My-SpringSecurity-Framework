package com.wang.spring_security_framework.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    //控制首页跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login/login");
        registry.addViewController("/index").setViewName("index");
    }

//    //拦截器配置
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        //注册拦截器
//        InterceptorRegistration registration = registry.addInterceptor(verifyCodeInterceptor);
//        registration.addPathPatterns("/login/**");
//    }
}
