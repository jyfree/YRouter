package com.jy.yrouter.service;


import androidx.annotation.NonNull;

import com.jy.yrouter.utils.ProviderPool;

/**
 * @description 默认的Factory，先尝试Provider，再尝试无参数构造
 * @date: 2020/4/21 11:19
 * @author: jy
 */

public class DefaultFactory implements IFactory {

    public static final DefaultFactory INSTANCE = new DefaultFactory();

    private DefaultFactory() {

    }

    @NonNull
    @Override
    public <T> T create(@NonNull Class<T> clazz) throws Exception {
        T t = ProviderPool.create(clazz);
        if (t != null) {
            return t;
        } else {
            return clazz.newInstance();
        }
    }
}
