package com.jy.yrouter.common;


import com.jy.yrouter.annotation.RouterPage;
import com.jy.yrouter.components.AnnotationInit;

/**
 * 用于加载 {@link RouterPage} 注解配置的节点。
 * 每个配置了 {@link RouterPage} 注解和注解生成器(annotationProcessor)的Application/Library模块，
 * 都会生成一个此接口的实现类，并在 {@link PageAnnotationHandler} 初始化时被加载。
 *
 * Created by jzj on 2018/3/29.
 */

public interface IPageAnnotationInit extends AnnotationInit<PageAnnotationHandler> {

    @Override
    void init(PageAnnotationHandler handler);
}
