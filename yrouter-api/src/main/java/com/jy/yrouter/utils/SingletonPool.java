package com.jy.yrouter.utils;


import androidx.annotation.NonNull;

import com.jy.yrouter.components.RouterComponents;
import com.jy.yrouter.service.IFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 单例缓存
 * @date: 2020/4/21 11:21
 * @author: jy
 */
public class SingletonPool {

    private static final Map<Class, Object> CACHE = new HashMap<>();

    @SuppressWarnings("unchecked")
    public static <I, T extends I> T get(Class<I> clazz, IFactory factory) throws Exception {
        if (clazz == null) {
            return null;
        }
        if (factory == null) {
            factory = RouterComponents.getDefaultFactory();
        }
        Object instance = getInstance(clazz, factory);
        RLogUtils.iFormat("[SingletonPool]   get instance of class = %s, result = %s", clazz, instance);
        return (T) instance;
    }

    @NonNull
    private static Object getInstance(@NonNull Class clazz, @NonNull IFactory factory) throws Exception {
        Object t = CACHE.get(clazz);
        if (t != null) {
            return t;
        } else {
            synchronized (CACHE) {
                t = CACHE.get(clazz);
                if (t == null) {
                    RLogUtils.iFormat("[SingletonPool] >>> create instance: %s", clazz);
                    t = factory.create(clazz);
                    //noinspection ConstantConditions
                    if (t != null) {
                        CACHE.put(clazz, t);
                    }
                }
            }
            return t;
        }
    }
}
