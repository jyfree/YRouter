package com.jy.yrouter.core;


import androidx.annotation.NonNull;

/**
 * @description 拦截Postcard跳转并做处理，支持异步操作。
 * @date: 2020/4/2 11:06
 * @author: jy
 */
public interface Interceptor {

    /**
     * 处理完成后，要调用 {@link InterceptCallback#onNext()} 或 {@link InterceptCallback#onComplete(int)} 方法
     */
    void intercept(@NonNull Postcard postcard, @NonNull InterceptCallback callback);
}
