package com.wang.spring_security_framework.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDTO {

    //用户id
    private String id;

    //用户名
    private String username;

    //密码
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
