package com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityFilter;

import cn.hutool.core.util.StrUtil;
import com.wang.spring_security_framework.common.SecurityConstant;
import com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityConfigUtil.JWTUtil;
import com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityConfigUtil.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//JWT校验过滤器
public class JwtFilter extends OncePerRequestFilter {

    private JWTUtil jwtUtil;
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //从header中获取JWT
        String jwtToken = request.getHeader(SecurityConstant.HEADER);
        if (StrUtil.isNotBlank(jwtToken) && jwtToken.startsWith(SecurityConstant.TOKEN_SPLIT)) {
            jwtToken = jwtToken.substring(SecurityConstant.TOKEN_SPLIT.length());
            //如果去掉头部的"Bearer "后不为空
            if (StrUtil.isNotBlank(jwtToken)) {
                //对JWT进行解码
                Claims claims = jwtUtil.JWTDecoder(jwtToken);
                //获得当前用户名
                String username = claims.getSubject();
                //获取授权的列表 用SpringSecurity内置的方法将逗号间隔转为list
                List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get(SecurityConstant.AUTHORITIES));

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    //从已有的user缓存中取了出user信息
                    User user = userRepository.findByUsername(username);
                    //检查token是否有效
                    if ()

                }
            }
        }


        //创建一个UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, authorities);
        //放到当前的Context中
        SecurityContextHolder.getContext().setAuthentication(token);
        //继续过滤器链的请求
        filterChain.doFilter(request, response);
    }

}
