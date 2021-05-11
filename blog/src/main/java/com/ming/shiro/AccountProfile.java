package com.ming.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * 公开账号信息
 */

@Data
public class AccountProfile implements Serializable {

    private Long userId;

    private String username;

    private String avatar;

    private String email;

}
