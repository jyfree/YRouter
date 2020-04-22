package com.jy.yrouter.core;

import androidx.annotation.NonNull;

import com.jy.yrouter.utils.RLogUtils;

/**
 * @description 路由处理器
 * @date: 2020/4/2 11:25
 * @author: jy
 */
public abstract class PostcardHandler {
    //拦截器责任链
    protected ChainedInterceptor mInterceptor;

    public PostcardHandler addInterceptor(@NonNull Interceptor interceptor) {
        if (mInterceptor == null) {
            mInterceptor = new ChainedInterceptor();
        }
        mInterceptor.addInterceptor(interceptor);
        return this;
    }

    public PostcardHandler addInterceptors(Interceptor... interceptors) {
        if (interceptors != null && interceptors.length > 0) {
            if (mInterceptor == null) {
                mInterceptor = new ChainedInterceptor();
            }
            for (Interceptor interceptor : interceptors) {
                mInterceptor.addInterceptor(interceptor);
            }
        }
        return this;
    }

    public void handle(@NonNull final Postcard postcard, @NonNull final InterceptCallback callback) {
        if (shouldHandle(postcard)) {
            RLogUtils.i("路由处理器：%s-- 处理Postcard：%s", this, postcard);
            if (mInterceptor != null && !postcard.isSkipInterceptors()) {
                mInterceptor.intercept(postcard, new InterceptCallback() {
                    @Override
                    public void onNext() {
                        handleInternal(postcard, callback);
                    }

                    @Override
                    public void onComplete(int result) {
                        callback.onComplete(result);
                    }
                });
            } else {
                handleInternal(postcard, callback);
            }
        } else {
            RLogUtils.i("路由处理器：%s-- 不处理Postcard：%s", this, postcard);
            callback.onNext();
        }
    }

    /**
     * 是否要处理给定的Postcard。在 {@link Interceptor} 之前调用。
     */
    protected abstract boolean shouldHandle(@NonNull Postcard postcard);

    /**
     * 处理Postcard。在 {@link Interceptor} 之后调用。
     */
    protected abstract void handleInternal(@NonNull Postcard postcard, @NonNull InterceptCallback callback);
}
