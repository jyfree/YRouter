package com.jy.yrouter.components;


import com.jy.yrouter.core.PostcardHandler;

/**
 * @description 用于初始化注解
 * @date: 2020/4/21 11:15
 * @author: jy
 */
public interface AnnotationInit<T extends PostcardHandler> {

    void init(T handler);
}
