package com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityConfigUtil;

import com.wang.spring_security_framework.common.SecurityConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//JWT工具类
@Component
public class JWTUtil {
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_USERNAME = "sub";

    //生成JWT
    public String JWTCreator(Authentication authResult) {
        //获取登录用户的角色
        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        StringBuffer stringBuffer = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            stringBuffer.append(authority.getAuthority()).append(",");
        }
        String username = authResult.getName();
        //自定义属性
        Map<String, Object> claims = new HashMap<>();
        //自定义属性, 放入用户拥有的权限
        claims.put(SecurityConstant.AUTHORITIES, stringBuffer);
        //自定义属性, 放入创建时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        //自定义属性, 放入主题, 即用户名
        claims.put(CLAIM_KEY_USERNAME, username);

        return Jwts.builder()
                //自定义属性
                .setClaims(claims)
                //过期时间
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                //签名
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.JWT_SIGN_KEY)
                .compact();
    }

    //生成Token的Claims, 调用下面的方法, 返回一个JWT
    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //获取用户名, 使用sub作为key和设置subject是一样的
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        //获取登录用户的角色
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        StringBuffer stringBuffer = new StringBuffer();
        for (GrantedAuthority authority : authorities) {
            stringBuffer.append(authority.getAuthority()).append(",");
        }
        claims.put(SecurityConstant.AUTHORITIES, stringBuffer);
        //获取创建时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    //根据Claims生成JWT
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.JWT_SIGN_KEY)
                .compact();
    }

    //解析JWT, 获得Claims
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SecurityConstant.JWT_SIGN_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            //如果过期,在异常中调用, 返回claims, 否则无法解析过期的token
            claims = e.getClaims();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    //从JWT中获得用户名
    public String getUsernameFromToken(String token) {
        try {
            return getClaimsFromToken(token).getSubject();
        } catch (ExpiredJwtException e) {
            //如果过期, 需要在此处异常调用如下的方法, 否则拿不到用户名
            return e.getClaims().getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
//        catch (Exception e) {
//            username = null;
//        }
    }

    //从JWT中获取创建时间 ==> 在自定义区域内
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    //从JWT中获取过期时间
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    //判断JWT是否过期
    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        //判断过期时间是否在当前时间之前
        return expiration.before(new Date());
    }

    //JWT是否可以被刷新(过期就可以被刷新)
    public Boolean canTokenBeRefreshed(String token) {
        return isTokenExpired(token);
    }

    //刷新JWT
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            //获得Token的Claims, 由于在生成JWT的时候会根据当前时间更新过期时间, 我们只需要手动修改
            //放在自定义属性中的创建时间就可以了
            Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            //利用修改后的claims再次生成token, 就不需要我们每次都去查用户的信息和权限了
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    //判断Token是否合法
    public Boolean validateToken(String token, UserDetails userDetails) {
        User user = (User) userDetails;
        String username = getUsernameFromToken(token);
        return (
                //如果用户名与token一致且token没有过期, 则认为合法
                username.equals(user.getUsername())
                && !isTokenExpired(token)
                );
    }

}
