package com.jy.yrouter.exception;

/**
 * @description
 * @date: 2020/4/21 11:56
 * @author: jy
 */
public class DefaultServiceException extends RuntimeException {

    public DefaultServiceException(String msg) {
        super(msg);
    }

    public static DefaultServiceException foundMoreThanOneImpl(Class service) {
        return new DefaultServiceException("因为" + service.getCanonicalName() + "有多个实现类,Router无法决定默认使用哪个来构造实例;"
                + "你可以通过@RouterService的defaultImpl参数设置一个默认的实现类");
    }
}
