package com.jy.yrouter.utils;


import androidx.annotation.NonNull;

import com.jy.yrouter.annotation.RouterProvider;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * @description Provider缓存
 * @date: 2020/4/21 11:23
 * @author: jy
 */
public class ProviderPool {

    private static final HashMap<Class, Method> CACHE = new HashMap<>();

    private static final Method NOT_FOUND = ProviderPool.class.getDeclaredMethods()[0];

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }
        Method provider = getProvider(clazz);
        if (provider == NOT_FOUND) {
            RLogUtils.iFormat("[ProviderPool] provider not found: %s", clazz);
            return null;
        } else {
            RLogUtils.iFormat("[ProviderPool] provider found: %s", provider);
            try {
                return (T) provider.invoke(null);
            } catch (Exception e) {
                RLogUtils.e(e);
            }
        }
        return null;
    }

    @NonNull
    private static <T> Method getProvider(@NonNull Class<T> clazz) {
        Method provider = CACHE.get(clazz);
        if (provider == null) {
            synchronized (CACHE) {
                provider = CACHE.get(clazz);
                if (provider == null) {
                    provider = findProvider(clazz);
                    CACHE.put(clazz, provider);
                }
            }
        }
        return provider;
    }

    @NonNull
    private static Method findProvider(@NonNull Class clazz) {
        RLogUtils.iFormat("[ProviderPool] >>> find provider with reflection: %s", clazz);
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getAnnotation(RouterProvider.class) != null) {
                if (Modifier.isStatic(method.getModifiers()) &&
                        method.getReturnType() == clazz &&
                        RouterUtils.isEmpty(method.getParameterTypes())) {
                    return method;
                } else {
                    RLogUtils.e("[ProviderPool] RouterProvider注解的应该是静态无参数方法，且返回值类型为当前Class");
                    return NOT_FOUND;
                }
            }
        }
        return NOT_FOUND;
    }
}
