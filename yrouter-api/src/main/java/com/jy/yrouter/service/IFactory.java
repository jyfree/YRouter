package com.jy.yrouter.service;


import androidx.annotation.NonNull;

/**
 * @description 从Class构造实例
 * @date: 2020/4/21 11:19
 * @author: jy
 */
public interface IFactory {

    @NonNull
    <T> T create(@NonNull Class<T> clazz) throws Exception;
}
