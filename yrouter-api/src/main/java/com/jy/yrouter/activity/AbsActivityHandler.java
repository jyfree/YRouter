package com.jy.yrouter.activity;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.jy.yrouter.components.ActivityLauncher;
import com.jy.yrouter.components.RouterComponents;
import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.PostcardHandler;
import com.jy.yrouter.core.ResultCode;
import com.jy.yrouter.utils.PostcardSourceTools;
import com.jy.yrouter.utils.RLogUtils;


/**
 * @description 跳转Activity的 {@link PostcardHandler}
 * @date: 2020/4/20 15:49
 * @author: jy
 */
public abstract class AbsActivityHandler extends PostcardHandler {

    @Override
    protected boolean shouldHandle(@NonNull Postcard postcard) {
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void handleInternal(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        // 创建Intent
        Intent intent = createIntent(postcard);
        if (intent == null || intent.getComponent() == null) {
            RLogUtils.e("AbsActivityHandler.createIntent()应返回的带有ClassName的显式跳转Intent");
            callback.onComplete(ResultCode.CODE_ERROR);
            return;
        }
        intent.setData(postcard.getUri());
        PostcardSourceTools.setIntentSource(intent, postcard);
        // 启动Activity
        postcard.putFieldIfAbsent(ActivityLauncher.FIELD_LIMIT_PACKAGE, limitPackage());
        int resultCode = RouterComponents.startActivity(postcard, intent);
        // 回调方法
        onActivityStartComplete(postcard, resultCode);
        // 完成
        callback.onComplete(resultCode);
    }

    /**
     * 是否只启动当前APP中的Activity
     *
     * @see ActivityLauncher#FIELD_LIMIT_PACKAGE
     */
    protected boolean limitPackage() {
        return true;
    }

    /**
     * 创建用于跳转的Intent，必须是带有ClassName的显式跳转Intent，可覆写添加特殊参数
     */
    @NonNull
    protected abstract Intent createIntent(@NonNull Postcard postcard);

    /**
     * 回调方法，子类可在此实现跳转动画等效果
     *
     * @param resultCode 跳转结果
     */
    protected void onActivityStartComplete(@NonNull Postcard postcard, int resultCode) {

    }

}
