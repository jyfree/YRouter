package com.jy.yrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 指定一个Uri跳转，此注解可以用在Activity和PostcardHandler上
 * @date: 2020/4/21 17:29
 * @author: jy
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterUri {

    /**
     * path
     */
    String[] path();

    /**
     * scheme
     */
    String scheme() default "";

    /**
     * host
     */
    String host() default "";

    /**
     * 是否允许外部跳转
     */
    boolean exported() default false;

    /**
     * 要添加的interceptors
     */
    Class[] interceptors() default {};
}
