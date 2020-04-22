package com.jy.yrouter.common;


import androidx.annotation.NonNull;

import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.PostcardHandler;
import com.jy.yrouter.core.ResultCode;


/**
 * @description 不支持的跳转链接，返回 {@link ResultCode#CODE_NOT_FOUND}
 * @date: 2020/4/21 17:48
 * @author: jy
 */

public class NotFoundHandler extends PostcardHandler {

    public static final NotFoundHandler INSTANCE = new NotFoundHandler();

    @Override
    public boolean shouldHandle(@NonNull Postcard postcard) {
        return true;
    }

    @Override
    protected void handleInternal(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        callback.onComplete(ResultCode.CODE_NOT_FOUND);
    }

    @Override
    public String toString() {
        return "NotFoundHandler";
    }
}
