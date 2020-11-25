//package com.wang.spring_security_framework.config.SpringSecurityConfig;
//
//import cn.hutool.core.util.StrUtil;
//import com.wang.spring_security_framework.common.SecurityConstant;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
////JWT认证过滤器
//public class JWTAuthenticationFilter extends BasicAuthenticationFilter {
//    //JWT过期时间
//    private Integer tokenExpirationTime;
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super(authenticationManager);
//    }
//
//    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
//        super(authenticationManager, authenticationEntryPoint);
//    }
//
//    //重写内部拦截器
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String header = request.getHeader(SecurityConstant.HEADER);
//        if (StrUtil.isBlank(header)) {
//            header = request.getParameter(SecurityConstant.HEADER);
//        }
//        //为空或者不以"Bearer "开头均为非法 JWT
//        Boolean notValid = StrUtil.isBlank(header) || (!header.startsWith(SecurityConstant.TOKEN_SPLIT));
//        if (notValid) {
//            //非法则交给下一个处理器
//            chain.doFilter(request, response);
//            return;
//        }
//        try {
////            UsernamePasswordAuthenticationToken 继承 AbstractAuthenticationToken 实现 Authentication
////            所以当在页面中输入用户名和密码之后首先会进入到 UsernamePasswordAuthenticationToken验证 (Authentication)，
//            UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(header, response);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception e) {
//            e.toString();
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    //UsernamePasswordAuthenticationToken 继承 AbstractAuthenticationToken 实现 Authentication
//    //所以当在页面中输入用户名和密码之后首先会进入到 UsernamePasswordAuthenticationToken验证(Authentication)，
//    private UsernamePasswordAuthenticationToken getAuthenticationToken(String header, HttpServletResponse response) {
//        String username = null;
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        Claims claims = Jwts.parser()
//                .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
//                .parseClaimsJws(header.replace(SecurityConstant.TOKEN_SPLIT, ""))
//                .getBody();
//        username = claims.getSubject();
//        String authority = claims.get(SecurityConstant.AUTHORITIES).toString();
//        if (!StrUtil.isEmpty(authority)) {
//            authorities.add(new SimpleGrantedAuthority(authority));
//        }
//
//        if (StrUtil.isNotBlank(username)) {
//            User principal = new User(username, "", authorities);
//            return  new UsernamePasswordAuthenticationToken(principal, null, authorities);
//        }
//    }
//}
