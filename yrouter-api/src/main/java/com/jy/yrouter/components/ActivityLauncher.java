package com.jy.yrouter.components;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.jy.yrouter.activity.StartActivityAction;
import com.jy.yrouter.core.Postcard;


/**
 * @description 用于启动Activity
 * @date: 2020/4/20 15:52
 * @author: jy
 */
public interface ActivityLauncher {

    String _PKG = "com.jy.yrouter.activity.";

    /**
     * 附加到Intent的Extra，{@link Bundle} 类型
     */
    String FIELD_INTENT_EXTRA = _PKG + "intent_extra";

    /**
     * 用于startActivityForResult的requestCode，int类型
     *
     * @see android.app.Activity#startActivityForResult(Intent, int)
     */
    String FIELD_REQUEST_CODE = _PKG + "request_code";

    /**
     * 设置Activity切换动画，int[]类型，长度为2
     *
     * @see android.app.Activity#overridePendingTransition(int, int)
     */
    String FIELD_START_ACTIVITY_ANIMATION = _PKG + "animation";

    /**
     * 设置 {@link ActivityOptions}，{@link Bundle} 类型
     *
     * @see ActivityOptions
     */
    String FIELD_START_ACTIVITY_OPTIONS = _PKG + "options";

    /**
     * 设置Intent的Flags，int型
     *
     * @see Intent#setFlags(int)
     */
    String FIELD_START_ACTIVITY_FLAGS = _PKG + "flags";

    /**
     * 是否限制Intent的packageName，限制后只会启动当前APP的页面，不启动外部页面，bool型
     *
     * @see Intent#setPackage(String)
     * @see Context#getPackageName()
     */
    String FIELD_LIMIT_PACKAGE = _PKG + "limit_package";

    /**
     * 覆盖startActivity操作
     *
     * @see StartActivityAction
     */
    String FIELD_START_ACTIVITY_ACTION = _PKG + "start_activity_action";

    /**
     * 启动的Activity类型，可用于埋点统计等用途，
     * 取值：{@link #INTERNAL_ACTIVITY}, {@link #EXTERNAL_ACTIVITY}
     */
    String FIELD_STARTED_ACTIVITY = _PKG + "started_activity";

    /**
     * 内部Activity（App自身）
     */
    int INTERNAL_ACTIVITY = 1;

    /**
     * 外部Activity（其他App）
     */
    int EXTERNAL_ACTIVITY = 2;

    /**
     * 启动Activity，返回ResultCode
     */
    int startActivity(@NonNull Postcard postcard, @NonNull Intent intent);
}
