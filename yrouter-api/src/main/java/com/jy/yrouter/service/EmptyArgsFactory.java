package com.jy.yrouter.service;


import androidx.annotation.NonNull;

/**
 * @description 无参数构造
 * @date: 2020/4/21 11:19
 * @author: jy
 */
public class EmptyArgsFactory implements IFactory {

    public static final EmptyArgsFactory INSTANCE = new EmptyArgsFactory();

    private EmptyArgsFactory() {

    }

    @NonNull
    @Override
    public <T> T create(@NonNull Class<T> clazz) throws Exception {
        return clazz.newInstance();
    }
}
