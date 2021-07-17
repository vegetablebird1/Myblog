package com.ming.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ming
 * @data 2021/7/17 21:40
 */

@AllArgsConstructor
@Getter
public enum ResponseCode {

    SUCCESS(200, "操作成功"),
    FAILED(400, "操作失败"),
    FORBIDDEN(401, "你没有该权限,请联系管理员"),
    NOT_FOUND(404, "找不到目标资源");

    private final Integer code;
    private final String msg;
}
