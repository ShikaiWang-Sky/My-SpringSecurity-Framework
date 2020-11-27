package com.wang.spring_security_framework.config.SpringSecurityConfig.SpringSecurityFilter;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

//默认的提取用户名和密码是通过 request.getParameter() 方法来提取的, 所以通过form我们可以提取到
//但是如果我们用ajax传递的话, 就提取不到了, 需要自己写过滤器!
//这里不能写 @Component, 因为我们要在SpringSecurity配置类中注册 myCustomAuthenticationFilter 并配置
//否则会爆出重名的Bean!
public class MyCustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //如果request请求是一个json同时编码方式为UTF-8
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
            UsernamePasswordAuthenticationToken authRequest = null;
            
            Map<String, String> authenticationBean = null;
            try (InputStream inputStream = request.getInputStream()) {
                //将JSON转为map
                authenticationBean = JSON.parseObject(inputStream, Map.class);
                //将用户名和密码放入 authRequest
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.get("username"), authenticationBean.get("password"));
            } catch (IOException e) {
                e.printStackTrace();
                //出现IO异常, 放空的用户信息
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            } finally {
                //将请求 request 和解析后的用户信息 authRequest 放入userDetails中
                setDetails(request, authRequest);
                //将我们前端传递的JSON对象继续放在request里传递, 这样我们就可以在认证成功的处理器中拿到它了!
                request.setAttribute("authInfo", authenticationBean);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        } else {
            return super.attemptAuthentication(request, response);
        }
    }
}
