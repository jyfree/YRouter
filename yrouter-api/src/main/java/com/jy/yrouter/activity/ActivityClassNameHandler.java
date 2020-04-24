package com.jy.yrouter.activity;

import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.utils.RLogUtils;

/**
 * @description 通过ClassName跳转Activity的 {@link com.jy.yrouter.core.PostcardHandler}
 * @date: 2020/4/20 16:40
 * @author: jy
 */
public class ActivityClassNameHandler extends AbsActivityHandler {

    @NonNull
    private final String mClassName;

    public ActivityClassNameHandler(@NonNull String className) {
        if (TextUtils.isEmpty(className)) {
            RLogUtils.e(new NullPointerException("className不应该为空"));
        }
        mClassName = className;
    }

    @NonNull
    @Override
    protected Intent createIntent(@NonNull Postcard postcard) {
        return new Intent().setClassName(postcard.getContext(), mClassName);
    }

    @Override
    public String toString() {
        return "ActivityClassNameHandler (" + mClassName + ")";
    }
}
