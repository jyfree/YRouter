package com.jy.yrouter.common;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;

import com.jy.yrouter.activity.StartActivityAction;
import com.jy.yrouter.components.ActivityLauncher;
import com.jy.yrouter.core.OnCompleteListener;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.PostcardHandler;
import com.jy.yrouter.utils.PostcardSourceTools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @description 默认封装Postcard，增加了常用参数的辅助方法，方便使用
 * @date: 2020/4/21 17:13
 * @author: jy
 */
public class DefaultPostcard extends Postcard {

    public DefaultPostcard(@NonNull Context context, @NonNull Uri uri) {
        super(context, uri);
    }

    public DefaultPostcard(@NonNull Context context, @NonNull String uri) {
        super(context, uri);
    }

    public DefaultPostcard(@NonNull Context context, @NonNull String uri,
                           HashMap<String, Object> extra) {
        super(context, uri, extra);
    }

    @Override
    public DefaultPostcard onComplete(OnCompleteListener listener) {
        return (DefaultPostcard) super.onComplete(listener);
    }

    @Override
    public DefaultPostcard setErrorMessage(String message) {
        return (DefaultPostcard) super.setErrorMessage(message);
    }

    @Override
    public DefaultPostcard setResultCode(int resultCode) {
        return (DefaultPostcard) super.setResultCode(resultCode);
    }

    @Override
    public DefaultPostcard setSkipInterceptors(boolean skipInterceptors) {
        return (DefaultPostcard) super.setSkipInterceptors(skipInterceptors);
    }


    /**
     * 往URI中添加共通参数。注意只对配置了 {@link UriParamInterceptor} 的 {@link PostcardHandler} 有效。
     *
     * @see UriParamInterceptor
     */
    public DefaultPostcard appendParams(HashMap<String, String> params) {
        putField(UriParamInterceptor.FIELD_URI_APPEND_PARAMS, params);
        return this;
    }

    /**
     * 是否使用 {@link StartUriHandler} 尝试通过Uri隐式跳转，默认为true
     */
    public DefaultPostcard tryStartUri(boolean value) {
        putField(StartUriHandler.FIELD_TRY_START_URI, value);
        return this;
    }

    /**
     * 设置跳转来源
     *
     * @see PostcardSourceTools
     */
    public DefaultPostcard from(int from) {
        putField(PostcardSourceTools.FIELD_FROM, from);
        return this;
    }

    /**
     * 用于startActivityForResult的requestCode
     *
     * @see android.app.Activity#startActivityForResult(Intent, int)
     */
    public DefaultPostcard activityRequestCode(int requestCode) {
        putField(ActivityLauncher.FIELD_REQUEST_CODE, requestCode);
        return this;
    }

    /**
     * 设置Activity切换动画
     *
     * @see android.app.Activity#overridePendingTransition(int, int)
     */
    public DefaultPostcard overridePendingTransition(int enterAnim, int exitAnim) {
        putField(ActivityLauncher.FIELD_START_ACTIVITY_ANIMATION, new int[]{enterAnim, exitAnim});
        return this;
    }

    /**
     * 覆盖startActivity操作
     *
     * @see StartActivityAction
     */
    public DefaultPostcard overrideStartActivity(StartActivityAction action) {
        putField(ActivityLauncher.FIELD_START_ACTIVITY_ACTION, action);
        return this;
    }

    /**
     * 设置Intent的Flags
     *
     * @see Intent#setFlags(int)
     */
    public DefaultPostcard setIntentFlags(int flags) {
        putField(ActivityLauncher.FIELD_START_ACTIVITY_FLAGS, flags);
        return this;
    }

    /**
     * 设置ActivityOptionsCompat
     *
     * @see ActivityOptions
     * @see ActivityOptionsCompat
     */
    public DefaultPostcard setActivityOptionsCompat(ActivityOptionsCompat options) {
        if (options != null) {
            putField(ActivityLauncher.FIELD_START_ACTIVITY_OPTIONS, options.toBundle());
        }
        return this;
    }

    /**
     * 是否限制Intent的packageName，限制后只会启动当前APP的页面，不启动外部页面，bool型
     *
     * @see ActivityLauncher#FIELD_LIMIT_PACKAGE
     */
    public DefaultPostcard limitPackage(boolean limit) {
        putField(ActivityLauncher.FIELD_LIMIT_PACKAGE, limit);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, boolean value) {
        extra().putBoolean(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, byte value) {
        extra().putByte(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, char value) {
        extra().putChar(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, short value) {
        extra().putShort(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, int value) {
        extra().putInt(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, long value) {
        extra().putLong(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, float value) {
        extra().putFloat(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, double value) {
        extra().putDouble(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, String value) {
        extra().putString(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, CharSequence value) {
        extra().putCharSequence(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, Parcelable value) {
        extra().putParcelable(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, Parcelable[] value) {
        extra().putParcelableArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putIntentParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        extra().putParcelableArrayList(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putIntentIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        extra().putIntegerArrayList(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putIntentStringArrayListExtra(String name, ArrayList<String> value) {
        extra().putStringArrayList(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putIntentCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        extra().putCharSequenceArrayList(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, Serializable value) {
        extra().putSerializable(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, boolean[] value) {
        extra().putBooleanArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, byte[] value) {
        extra().putByteArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, short[] value) {
        extra().putShortArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, char[] value) {
        extra().putCharArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, int[] value) {
        extra().putIntArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, long[] value) {
        extra().putLongArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, float[] value) {
        extra().putFloatArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, double[] value) {
        extra().putDoubleArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, String[] value) {
        extra().putStringArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, CharSequence[] value) {
        extra().putCharSequenceArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtra(String name, Bundle value) {
        extra().putBundle(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public DefaultPostcard putExtras(Bundle extras) {
        if (extras != null) {
            extra().putAll(extras);
        }
        return this;
    }

    @NonNull
    private synchronized Bundle extra() {
        Bundle extra = getField(Bundle.class, ActivityLauncher.FIELD_INTENT_EXTRA, null);
        if (extra == null) {
            extra = new Bundle();
            putField(ActivityLauncher.FIELD_INTENT_EXTRA, extra);
        }
        return extra;
    }

    public static void startFromProxyActivity(final Activity activity, OnCompleteListener listener) {
        if (activity == null) {
            return;
        }
        Intent intent = activity.getIntent();
        if (intent == null) {
            activity.finish();
            return;
        }
        Uri data = intent.getData();
        if (data == null) {
            activity.finish();
            return;
        }
        Bundle extras = intent.getExtras();
        new DefaultPostcard(activity, data)
                // 设置为外部跳转
                .from(PostcardSourceTools.FROM_EXTERNAL)
                // 禁止通过Uri隐式跳转，避免Router又打开本Activity造成死循环
                .tryStartUri(false)
                // 异步监听
                .onComplete(listener)
                .putExtras(extras)
                .start();
    }
}
