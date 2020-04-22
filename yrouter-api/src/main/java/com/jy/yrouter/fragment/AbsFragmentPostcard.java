package com.jy.yrouter.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.jy.yrouter.components.ActivityLauncher;
import com.jy.yrouter.core.Postcard;

import java.io.Serializable;


/**
 * @description 带参数的fragment路由容器基类 支持了extra参数
 * @date: 2020/4/21 10:38
 * @author: jy
 */
public abstract class AbsFragmentPostcard extends Postcard {

    public AbsFragmentPostcard(@NonNull Context context, String uri) {
        super(context, uri);
    }

    @Override
    public void start() {
        putField(StartFragmentAction.START_FRAGMENT_ACTION, getStartFragmentAction());
        super.start();
    }

    protected abstract StartFragmentAction getStartFragmentAction();

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, Serializable value) {
        extra().putSerializable(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, boolean[] value) {
        extra().putBooleanArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, byte[] value) {
        extra().putByteArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, short[] value) {
        extra().putShortArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, char[] value) {
        extra().putCharArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, int[] value) {
        extra().putIntArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, long[] value) {
        extra().putLongArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, float[] value) {
        extra().putFloatArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, double[] value) {
        extra().putDoubleArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, String[] value) {
        extra().putStringArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, CharSequence[] value) {
        extra().putCharSequenceArray(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtra(String name, Bundle value) {
        extra().putBundle(name, value);
        return this;
    }

    /**
     * 附加到Intent的Extra
     */
    public AbsFragmentPostcard putExtras(Bundle extras) {
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
}
