package com.wang.spring_security_framework.common;

public interface SecurityConstant {
    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer ";

    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = "myJWTSignKey";

    /**
     * token请求头
     */
    String HEADER = "accessToken";

    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    /**
     * JWT过期时间, 毫秒
     */
    Long EXPIRATION_TIME = (long) (6 * 60 * 1000);
}
