package com.ming.common.exception;

import com.ming.common.enumeration.ArticleStatusCode;

/**
 * @author ming
 * @data 2021/7/12 23:29
 */

public class ArticleException extends RuntimeException {

    public ArticleException(String message) {
        super(message);
    }

    public ArticleException(ArticleStatusCode code) {
        super(code.getMes());
    }

    public ArticleException(ArticleStatusCode code, String detail) {
        super(code.getMes() + ": " + detail);
    }

    public ArticleException(String message, Throwable cause) {
        super(message, cause);
    }
}
