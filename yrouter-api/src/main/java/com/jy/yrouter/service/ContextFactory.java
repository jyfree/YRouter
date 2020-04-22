package com.jy.yrouter.service;

import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @description 使用Context构造
 * @date: 2020/4/21 11:20
 * @author: jy
 */
public class ContextFactory implements IFactory {

    private final Context mContext;

    public ContextFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T> T create(@NonNull Class<T> clazz) throws Exception {
        return clazz.getConstructor(Context.class).newInstance(mContext);
    }
}
