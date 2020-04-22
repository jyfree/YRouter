package com.jy.yrouter.components;

import com.jy.yrouter.core.PostcardHandler;

/**
 * @description 用于加载注解配置
 * @date: 2020/4/21 11:15
 * @author: jy
 */
public interface AnnotationLoader {

    <T extends PostcardHandler> void load(T handler, Class<? extends AnnotationInit<T>> initClass);
}
