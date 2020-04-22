package com.jy.yrouter.utils;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.jy.yrouter.core.Postcard;

/**
 * @description 跳转来源相关。跳转来源可用于权限控制(exported)、埋点统计等功能。
 * 在 {@link Postcard} 中设置的 {@link #FIELD_FROM} ，会被带到Intent的参数
 * {@link #INTENT_KEY_URI_FROM} 中，Activity也可以根据此参数做特殊逻辑处理。
 * @date: 2020/4/20 14:23
 * @author: jy
 */
public class PostcardSourceTools {

    /**
     * 无效来源
     */
    public static final int FROM_INVALID = 0;
    /**
     * 外部跳转
     */
    public static final int FROM_EXTERNAL = FROM_INVALID + 1;
    /**
     * 内部跳转
     */
    public static final int FROM_INTERNAL = FROM_EXTERNAL + 1;
    /**
     * 从WebView跳转
     */
    public static final int FROM_WEBVIEW = FROM_INTERNAL + 1;


    public static final String FIELD_FROM = "com.jy.yrouter.from";

    /**
     * Intent中Scheme跳转来源参数的Key
     */
    public static final String INTENT_KEY_URI_FROM = "com.jy.yrouter.from";

    public static boolean disableExportedControl = false;

    /**
     * 是否禁用外部跳转控制
     */
    public static void setDisableExportedControl(boolean disable) {
        disableExportedControl = disable;
    }

    /**
     * 跳转来源控制
     */
    public static boolean shouldHandle(Postcard postcard, boolean exported) {
        return disableExportedControl || exported || getSource(postcard) != FROM_EXTERNAL;
    }

    public static void setSource(Postcard postcard, int from) {
        if (postcard != null) {
            postcard.putField(FIELD_FROM, from);
        }
    }

    public static int getSource(@NonNull Postcard postcard) {
        return getSource(postcard, FROM_INTERNAL);
    }

    public static int getSource(@NonNull Postcard postcard, int defaultValue) {
        return postcard.getIntField(FIELD_FROM, defaultValue);
    }

    /**
     * 从postcard将source参数设置到intent中
     */
    public static void setIntentSource(Intent intent, Postcard postcard) {
        if (intent != null && postcard != null) {
            Integer result = postcard.getField(Integer.class, FIELD_FROM);
            if (result != null) {
                setSource(intent, result);
            }
        }
    }

    /**
     * 将source参数设置到intent中
     */
    public static void setSource(Intent intent, int source) {
        if (intent != null) {
            intent.putExtra(INTENT_KEY_URI_FROM, source);
        }
    }

    public static int getSource(Intent intent, int defaultValue) {
        return getInt(intent, INTENT_KEY_URI_FROM, defaultValue);
    }

    public static int getSource(Bundle bundle, int defaultValue) {
        return bundle == null ? defaultValue : bundle.getInt(INTENT_KEY_URI_FROM, defaultValue);
    }

    private static int getInt(Intent intent, String key, int defaultValue) {
        if (intent == null) {
            return defaultValue;
        }
        try {
            return intent.getIntExtra(key, defaultValue);
        } catch (Exception e) {
            RLogUtils.e(e);
        }
        return defaultValue;
    }
}
