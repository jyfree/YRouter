package com.jy.yrouter.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.jy.yrouter.components.DefaultActivityLauncher;
import com.jy.yrouter.core.Postcard;


/**
 * @description 启动Activity操作
 * @date: 2020/4/20 16:14
 * @author: jy
 */
public interface StartActivityAction {

    /**
     * <p>启动Activity操作（可在此修改Intent，设置动画等）。</p>
     *
     * <p>在执行 {@link Context#startActivity(Intent)} 前调用此方法。</p>
     *
     * <p>
     * 返回true：已经处理了startActivity操作。<br/>
     * 返回false：未处理，之后会继续执行默认的startActivity逻辑。
     * </p>
     *
     * @param intent 跳转要用的intent
     * @return 是否执行了startActivity操作
     * @see DefaultActivityLauncher
     */
    boolean startActivity(@NonNull Postcard postcard, @NonNull Intent intent)
            throws ActivityNotFoundException, SecurityException;
}
