package com.jy.yrouter.core;


import androidx.annotation.NonNull;

import com.jy.yrouter.utils.RLogUtils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * @description 拦截器责任链
 * @date: 2020/4/2 11:19
 * @author: jy
 */
public class ChainedInterceptor implements Interceptor {

    private final List<Interceptor> mInterceptors = new LinkedList<>();

    public void addInterceptor(@NonNull Interceptor interceptor) {
        mInterceptors.add(interceptor);
    }

    @Override
    public void intercept(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        next(mInterceptors.iterator(), postcard, callback);
    }

    private void next(@NonNull final Iterator<Interceptor> iterator, @NonNull final Postcard postcard,
                      @NonNull final InterceptCallback callback) {
        if (iterator.hasNext()) {
            Interceptor t = iterator.next();
            RLogUtils.iFormat("拦截器责任链---拦截器：%s, Postcard：%s", t.getClass().getSimpleName(), postcard.getUri());
            t.intercept(postcard, new InterceptCallback() {
                @Override
                public void onNext() {
                    next(iterator, postcard, callback);
                }

                @Override
                public void onComplete(int resultCode) {
                    callback.onComplete(resultCode);
                }
            });
        } else {
            callback.onNext();
        }
    }
}
