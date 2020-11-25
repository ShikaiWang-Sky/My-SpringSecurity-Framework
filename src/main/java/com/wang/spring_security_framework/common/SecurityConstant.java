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
     * token参数头
     */
    String HEADER = "accessToken";

    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    /**
     * 用户选择JWT保存时间参数头
     */
    String SAVE_LOGIN = "saveLogin";

    /**
     * 交互token前缀key
     */
    String TOKEN_PRE = "TTYY_TOKEN_PRE:";

    /**
     * 用户token前缀key 单点登录使用
     */
    String USER_TOKEN = "TTYY_USER_TOKEN:";

    /**
     * JWT过期时间, 毫秒
     */
    Long EXPIRATION_TIME = (long) (7 * 60 * 1000);
}
