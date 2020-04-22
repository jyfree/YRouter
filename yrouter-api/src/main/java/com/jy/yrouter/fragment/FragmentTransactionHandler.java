package com.jy.yrouter.fragment;


import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.jy.yrouter.components.ActivityLauncher;
import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.PostcardHandler;
import com.jy.yrouter.core.ResultCode;
import com.jy.yrouter.utils.RLogUtils;


/**
 * @description Fragment处理的的Handler
 * @date: 2020/4/21 10:41
 * @author: jy
 */
public final class FragmentTransactionHandler extends PostcardHandler {

    public final static String FRAGMENT_CLASS_NAME = "FRAGMENT_CLASS_NAME";

    @NonNull
    private final String mClassName;

    @NonNull
    public String getClassName() {
        return mClassName;
    }

    public FragmentTransactionHandler(@NonNull String className) {
        mClassName = className;
    }

    @Override
    protected boolean shouldHandle(@NonNull Postcard postcard) {
        return true;
    }

    @Override
    protected void handleInternal(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        if (TextUtils.isEmpty(mClassName)) {
            RLogUtils.e("FragmentTransactionHandler.handleInternal()应返回的带有ClassName");
            callback.onComplete(ResultCode.CODE_BAD_REQUEST);
            return;
        }

        StartFragmentAction action = postcard.getField(StartFragmentAction.class, StartFragmentAction.START_FRAGMENT_ACTION);
        if (action == null) {
            RLogUtils.e("FragmentTransactionHandler.handleInternal()应返回的带有StartFragmentAction");
            callback.onComplete(ResultCode.CODE_BAD_REQUEST);
            return;
        }

        if (!postcard.hasField(FRAGMENT_CLASS_NAME)) {
            //判断一下，便于被替换
            postcard.putField(FRAGMENT_CLASS_NAME, mClassName);
        }

        // Extra
        Bundle extra = postcard.getField(Bundle.class, ActivityLauncher.FIELD_INTENT_EXTRA);
        boolean success = action.startFragment(postcard, extra);
        // 完成
        callback.onComplete(success ? ResultCode.CODE_SUCCESS : ResultCode.CODE_BAD_REQUEST);
    }
}
