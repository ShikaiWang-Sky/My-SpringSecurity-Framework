package com.wang.spring_security_framework.service.serviceImpl;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.wang.spring_security_framework.service.CaptchaService;
import com.wang.spring_security_framework.util.CaptchaUtil;
import com.wang.spring_security_framework.util.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private UUIDUtil uuidUtil;
    @Autowired
    private CaptchaUtil captchaUtil;

    //从SpringBoot的配置文件中取出过期时间
    @Value("${server.servlet.session.timeout}")
    private Integer timeout;

    //UUID为key, 验证码为Value放在Redis中
    @Override
    public Map<String, Object> createToken(String captcha) {
        //生成一个token
        String key = uuidUtil.getUUID32();

        //生成验证码对应的token  以token为key  验证码为value存在redis中
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, captcha);
        //设置验证码过期时间
        redisTemplate.expire(key, timeout, TimeUnit.MINUTES);

        Map<String, Object> map = new HashMap<>();
        map.put("token", key);
        map.put("expire", timeout);
        return map;
    }

    //生成captcha验证码
    @Override
    public Map<String, Object> captchaCreator() throws IOException {
        return captchaUtil.catchaImgCreator();
    }

    //验证输入的验证码是否正确
    @Override
    public String versifyCaptcha(String token, String inputCode) {
        //根据前端传回的token在redis中找对应的value
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(token)) {
            //验证通过, 删除对应的key
            if (valueOperations.get(token).equals(inputCode)) {
                redisTemplate.delete(token);
                return "true";
            } else {
                return "false";
            }
        } else {
            return "false";
        }
    }
}
