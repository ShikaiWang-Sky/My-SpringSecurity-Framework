package com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityConfigUtil;

import cn.hutool.core.util.StrUtil;
import com.wang.spring_security_framework.common.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

//JWT工具类
@Component
public class JWTUtil {

    //生成JWT
    public String JWTCreator(Authentication authResult) {
        //获取登录用户的角色
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuffer stringBuffer = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            stringBuffer.append(authority.getAuthority()).append(" ");
        }
        String username = authResult.getName();

        return Jwts.builder()
                //自定义属性, 放入用户拥有的权限
                .claim(SecurityConstant.AUTHORITIES, stringBuffer)
                //主题, 放入用户名
                .setSubject(username)
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                //签名
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.JWT_SIGN_KEY)
                .compact();
    }

    //解析JWT
    public Claims JWTDecoder(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                //前端传过来的时候会多一个 "Bearer "，需要将其替换掉
                .parseClaimsJws(jwtToken.replace(SecurityConstant.TOKEN_SPLIT, ""))
                .getBody();
    }

}
