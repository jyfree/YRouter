package com.jy.yrouter.utils;

import android.app.Activity;

import com.jy.yrouter.activity.ActivityClassNameHandler;
import com.jy.yrouter.activity.ActivityHandler;
import com.jy.yrouter.common.NotExportedInterceptor;
import com.jy.yrouter.core.Interceptor;
import com.jy.yrouter.core.PostcardHandler;

import java.lang.reflect.Modifier;

/**
 * @description 跳转目标，支持ActivityClass, ActivityClassName, PostcardHandler。
 * @date: 2020/4/20 14:16
 * @author: jy
 */
public class PostcardTargetTools {

    public static PostcardHandler parse(Object target, boolean exported,
                                        Interceptor... interceptors) {
        PostcardHandler handler = toHandler(target);
        if (handler != null) {
            if (!exported) {
                handler.addInterceptor(NotExportedInterceptor.INSTANCE);
            }
            handler.addInterceptors(interceptors);
        }
        return handler;
    }

    private static PostcardHandler toHandler(Object target) {
        if (target instanceof PostcardHandler) {
            return (PostcardHandler) target;
        } else if (target instanceof String) {
            return new ActivityClassNameHandler((String) target);
        } else if (target instanceof Class && isValidActivityClass((Class) target)) {
            return new ActivityHandler((Class<? extends Activity>) target);
        } else {
            return null;
        }
    }

    private static boolean isValidActivityClass(Class clazz) {
        //isAssignableFrom 判断类对象是否相同，isAbstract判断是否为抽象类
        return clazz != null && Activity.class.isAssignableFrom(clazz)
                && !Modifier.isAbstract(clazz.getModifiers());
    }
}
