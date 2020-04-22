package com.jy.yrouter.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.utils.RLogUtils;


/**
 * @description 通过Fragment的Postcard 于 FragmentBuildPostcard.FRAGMENT 获取返回的Fragment
 * @date: 2020/4/21 10:50
 * @author: jy
 */
public class FragmentBuildPostcard extends AbsFragmentPostcard {
    public final static String FRAGMENT = "CUSTOM_FRAGMENT_OBJ";

    public FragmentBuildPostcard(@NonNull Context context, String uri) {
        super(context, uri);
    }

    @Override
    protected StartFragmentAction getStartFragmentAction() {
        return new StartFragmentAction() {
            @Override
            public boolean startFragment(@NonNull Postcard postcard, @NonNull Bundle bundle) throws ActivityNotFoundException, SecurityException {
                String fragmentClassName = postcard.getStringField(FragmentTransactionHandler.FRAGMENT_CLASS_NAME);
                if (TextUtils.isEmpty(fragmentClassName)) {
                    RLogUtils.e("FragmentBuildPostcard.handleInternal()应返回的带有ClassName");
                    return false;
                }
                try {
                    Fragment fragment = Fragment.instantiate(postcard.getContext(), fragmentClassName, bundle);
                    if (fragment == null) {
                        return false;
                    }
                    //自定义处理不做transaction，直接放在postcard里面回调
                    postcard.putField(FRAGMENT, fragment);
                    return true;
                } catch (Exception e) {
                    RLogUtils.e(e);
                    return false;
                }
            }
        };
    }
}
