package com.wang.spring_security_framework.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * t_role_permission
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class PermissionDTO implements Serializable {
    /**
     * 权限id
     */
    private String id;

    /**
     * 权限标识符
     */
    private String code;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 请求地址
     */
    private String url;

    private static final long serialVersionUID = 1L;
}
