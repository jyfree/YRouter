package com.jy.yrouter.components;


import com.jy.yrouter.Router;
import com.jy.yrouter.core.PostcardHandler;

import java.util.List;

/**
 * @description 使用ServiceLoader加载注解配置
 * @date: 2020/4/21 11:16
 * @author: jy
 */
public class DefaultAnnotationLoader implements AnnotationLoader {

    public static final AnnotationLoader INSTANCE = new DefaultAnnotationLoader();

    @Override
    public <T extends PostcardHandler> void load(T handler, Class<? extends AnnotationInit<T>> initClass) {
        List<? extends AnnotationInit<T>> services = Router.getAllServices(initClass);
        for (AnnotationInit<T> service : services) {
            service.init(handler);
        }
    }
}
