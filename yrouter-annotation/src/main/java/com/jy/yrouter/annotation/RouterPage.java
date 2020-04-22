package com.jy.yrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description 指定一个内部页面跳转，此注解可以用在Activity和PostcardHandler上
 * @date: 2020/4/21 17:49
 * @author: jy
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface RouterPage {

    /**
     * path
     */
    String[] path();

    /**
     * 要添加的interceptors
     */
    Class[] interceptors() default {};
}
