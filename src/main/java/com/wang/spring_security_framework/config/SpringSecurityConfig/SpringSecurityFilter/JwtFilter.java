package com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityFilter;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.wang.spring_security_framework.common.SecurityConstant;
import com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityConfigUtil.JWTUtil;
import com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityConfigUtil.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

//JWT校验过滤器
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从header中获取JWT
        String jwtToken = request.getHeader(SecurityConstant.HEADER);
        if (StrUtil.isNotBlank(jwtToken) && jwtToken.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            jwtToken = jwtToken.substring(SecurityConstant.TOKEN_SPLIT.length());
            //如果去掉头部的"Bearer "后不为空
            if (StrUtil.isNotBlank(jwtToken)) {
                //获得当前用户名
                String username = jwtUtil.getUsernameFromToken(jwtToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //从已有的user缓存中取了出user信息
                    User user = userRepository.findByUsername(username);

                    //token相关信息的map
                    Map<String, String> resultMap = new HashMap<>();
                    //检查token是否有效
                    if (jwtUtil.validateToken(jwtToken, user)) {
                        //创建一个标识符, 表示此时Token有效, 不需要更新
                        resultMap.put("needRefresh", "false");
                        request.setAttribute("auth", resultMap);
                        //创建一个UsernamePasswordAuthenticationToken
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        //设置用户登录状态 ==> 放到当前的Context中
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else if (username.equals(user.getUsername())) {
                        //如果用户名相同但是过期了, 刷新token (和缓存中的比较)
                        if (jwtUtil.canTokenBeRefreshed(jwtToken)) {
                            //TODO 将更新后的token更新到前端
                            String refreshedToken = jwtUtil.refreshToken(jwtToken);
                            resultMap.put("refreshedToken", refreshedToken);
                            //需要更新
                            resultMap.put("needRefresh", "true");
                            //将更新后的Token放到request中, 我们写一个controller, 从中取出后就可以更新了
                            request.setAttribute("auth", resultMap);
                            //创建一个UsernamePasswordAuthenticationToken
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            //设置用户登录状态 ==> 放到当前的Context中
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    }
                }
            }
        }
//        //创建一个UsernamePasswordAuthenticationToken
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
//        //放到当前的Context中
//        SecurityContextHolder.getContext().setAuthentication(token);
        //继续过滤器链的请求
        filterChain.doFilter(request, response);
    }
}
