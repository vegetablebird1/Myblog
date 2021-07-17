package com.ming.common.annotation;

import java.lang.annotation.*;

/**
 * @author ming
 * @data 2021/7/12 17:46
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyLog {
    String value() default "";
}
