package com.jy.yrouter.simple.advanced.handler;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.PostcardHandler;
import com.jy.yrouter.core.ResultCode;
import com.jy.yrouter.simple.constant.DemoConstant;

/**
 * @description 自定义PostcardHandler
 * @date: 2020/4/23 16:12
 * @author: jy
 */
@RouterUri(path = DemoConstant.SHOW_TOAST_HANDLER)
public class ShowToastHandler extends PostcardHandler {

    @Override
    protected boolean shouldHandle(@NonNull Postcard postcard) {
        return true;
    }

    @Override
    protected void handleInternal(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        Toast.makeText(postcard.getContext(), "TestHandler", Toast.LENGTH_LONG).show();
        callback.onComplete(ResultCode.CODE_SUCCESS);
    }
}