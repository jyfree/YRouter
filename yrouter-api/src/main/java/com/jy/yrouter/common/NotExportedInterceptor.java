package com.jy.yrouter.common;


import androidx.annotation.NonNull;

import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Interceptor;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.ResultCode;
import com.jy.yrouter.utils.PostcardSourceTools;

/**
 * @description 节点的exported为false，不允许来自外部的跳转，拦截并返回 {@link ResultCode#CODE_FORBIDDEN}
 * @date: 2020/4/20 14:30
 * @author: jy
 */
public class NotExportedInterceptor implements Interceptor {

    public static final NotExportedInterceptor INSTANCE = new NotExportedInterceptor();

    private NotExportedInterceptor() {
    }

    @Override
    public void intercept(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        if (PostcardSourceTools.shouldHandle(postcard, false)) {
            callback.onNext();
        } else {
            callback.onComplete(ResultCode.CODE_FORBIDDEN);
        }
    }
}
