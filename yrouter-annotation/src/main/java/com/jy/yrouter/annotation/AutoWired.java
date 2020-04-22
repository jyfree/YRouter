package com.jy.yrouter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description
 * @date: 2020/3/31 17:54
 * @author: jy
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface AutoWired {
    // Mark param's name or service name.
    String name() default "";

    // If required, app will be crash when value is null.
    // Primitive type wont be check!
    boolean required() default false;

    // Description of the field
    String desc() default "";
}
