package com.wang.spring_security_framework.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {

    /**
     * 生成32位的随机UUID
     * @return 字符形式的小写UUID
     */
    @Bean
    public String getUUID32() {
        return UUID.randomUUID().toString()
                .replace("-", "").toLowerCase();
    }
}
