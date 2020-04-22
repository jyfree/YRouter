package com.jy.yrouter.activity;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.jy.yrouter.core.Postcard;


/**
 * @description 通过Class跳转Activity的 {@link com.jy.yrouter.core.Postcard}
 * @date: 2020/4/20 16:42
 * @author: jy
 */
public class ActivityHandler extends AbsActivityHandler {

    @NonNull
    protected final Class<? extends Activity> mClazz;

    /**
     * @param clazz 要跳转的Activity
     */
    public ActivityHandler(@NonNull Class<? extends Activity> clazz) {
        mClazz = clazz;
    }

    @NonNull
    @Override
    protected Intent createIntent(@NonNull Postcard postcard) {
        return new Intent(postcard.getContext(), mClazz);
    }

    @Override
    public String toString() {
        return "ActivityHandler (" + mClazz.getSimpleName() + ")";
    }
}
