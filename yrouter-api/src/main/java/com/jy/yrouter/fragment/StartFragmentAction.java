package com.jy.yrouter.fragment;

import android.content.ActivityNotFoundException;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.jy.yrouter.core.Postcard;


public interface StartFragmentAction {
    String START_FRAGMENT_ACTION = "StartFragmentAction";

    /**
     * <p>启动Fragment操作。</p>
     *
     * @param bundle 跳转要用的bundle
     * @return 是否执行了startFragment操作
     */
    boolean startFragment(@NonNull Postcard postcard, @NonNull Bundle bundle)
            throws ActivityNotFoundException, SecurityException;
}
