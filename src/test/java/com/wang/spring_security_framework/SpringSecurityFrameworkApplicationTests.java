package com.wang.spring_security_framework;

import com.wang.spring_security_framework.common.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SpringSecurityFrameworkApplicationTests {

    @Test
    void JWTCreatorTest() {
        String jwt = Jwts.builder()
                //主题, 放入用户名
                .setSubject("admin")
                //自定义属性, 放入用户拥有的权限
                .claim("authorities", "管理员")
                //失效时间 7min
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 60 * 1000))
                //签名算法和密钥
                .signWith(SignatureAlgorithm.HS256, "tmax")
                .compact();
        System.out.println(jwt);
    }

    @Test
    void JWTDecoderTest() {

        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdGllcyI6InAxLHAyLCIsInN1YiI6ImFkbWluIiwiZXhwIjoxNjA2MjkzNzI0fQ.byyKWAWtPzxgxbFD94E0iWc6FVgfbrm1iJF6RwoO3Vg";
        //解析jwt
        Claims claims = Jwts.parser()
                .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                .parseClaimsJws(jwt)
                .getBody();
        System.out.println(claims);
        //获取用户名
        String username = claims.getSubject();
        System.out.println("username: " + username);
        //获取权限
        String authority = claims.get(SecurityConstant.AUTHORITIES).toString();
        System.out.println("authority: " + authority);
        Date expiration = claims.getExpiration();
        System.out.println("expiration: " + expiration);
    }

}
