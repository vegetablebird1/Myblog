package com.ming.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author ming
 * @data 2021/7/12 23:30
 */

@Getter
@AllArgsConstructor
public enum ArticleStatusCode {
    NOT_EXIST("文章不存在"),
    DELETED("文章已被删除");

    private final String mes;
}
