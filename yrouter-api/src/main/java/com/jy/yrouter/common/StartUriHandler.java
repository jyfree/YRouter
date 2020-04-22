package com.jy.yrouter.common;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.jy.yrouter.components.ActivityLauncher;
import com.jy.yrouter.components.RouterComponents;
import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.PostcardHandler;
import com.jy.yrouter.core.ResultCode;
import com.jy.yrouter.utils.PostcardSourceTools;

/**
 * @description 尝试直接用 {@link Intent#setData(Uri)} 隐式跳转启动Uri的Handler
 * @date: 2020/4/21 13:52
 * @author: jy
 */
public class StartUriHandler extends PostcardHandler {

    /**
     * 是否使用 {@link StartUriHandler} 尝试通过Uri隐式跳转，默认为true
     */
    public static final String FIELD_TRY_START_URI = "com.jy.yrouter.common.try_start_uri";

    @Override
    protected boolean shouldHandle(@NonNull Postcard postcard) {
        return postcard.getBooleanField(FIELD_TRY_START_URI, true);
    }

    @Override
    protected void handleInternal(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(postcard.getUri());
        PostcardSourceTools.setIntentSource(intent, postcard);
        postcard.putFieldIfAbsent(ActivityLauncher.FIELD_LIMIT_PACKAGE, limitPackage());
        int resultCode = RouterComponents.startActivity(postcard, intent);
        handleResult(callback, resultCode);
    }

    /**
     * 是否只启动当前APP中的Activity
     *
     * @see ActivityLauncher#FIELD_LIMIT_PACKAGE
     */
    protected boolean limitPackage() {
        return false;
    }

    /**
     * 跳转Activity后的行为，可以继承覆盖。
     * 默认行为：跳转成功后结束分发，跳转失败后继续分发给其他Handler。
     */
    protected void handleResult(@NonNull InterceptCallback callback, int resultCode) {
        if (resultCode == ResultCode.CODE_SUCCESS) {
            callback.onComplete(resultCode);
        } else {
            callback.onNext();
        }
    }

    @Override
    public String toString() {
        return "StartUriHandler";
    }
}
