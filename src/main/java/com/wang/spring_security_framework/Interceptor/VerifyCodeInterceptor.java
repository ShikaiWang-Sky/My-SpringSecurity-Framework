//package com.wang.spring_security_framework.Interceptor;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Component
////验证码拦截器
//public class VerifyCodeInterceptor implements HandlerInterceptor {
//
//    //在请求之前调用(Controller之前)
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("请求之前");
//        System.out.println(request.getParameter("username"));
//        System.out.println(request.getParameter("password"));
//        System.out.println(request.getParameter("inputCode"));
//        System.out.println(request.getParameter("token"));
//        return false;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
