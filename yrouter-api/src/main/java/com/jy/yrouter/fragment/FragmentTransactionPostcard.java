package com.jy.yrouter.fragment;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.utils.RLogUtils;

/**
 * @description Fragment跳转的Handler
 * @date: 2020/4/21 17:41
 * @author: jy
 */
public class FragmentTransactionPostcard extends AbsFragmentTransactionPostcard {

    private final FragmentManager mFragmentManager;

    /**
     * @param activity 父activity
     * @param path     地址
     */
    public FragmentTransactionPostcard(@NonNull FragmentActivity activity, String path) {
        super(activity, path);
        mFragmentManager = activity.getSupportFragmentManager();
    }

    /**
     * @param fragment 父fragment
     * @param path     地址
     */
    public FragmentTransactionPostcard(@NonNull Fragment fragment, String path) {
        super(fragment.getContext(), path);
        mFragmentManager = fragment.getChildFragmentManager();
    }

    /**
     * @param context         context
     * @param fragmentManager fragmentManager
     * @param uri             uri
     */
    public FragmentTransactionPostcard(@NonNull Context context, FragmentManager fragmentManager, String uri) {
        super(context, uri);
        mFragmentManager = fragmentManager;
    }

    @Override
    protected StartFragmentAction getStartFragmentAction() {
        return new BuildStartFragmentAction(mFragmentManager, mContainerViewId, mType, mAllowingStateLoss, mTag);
    }

    static class BuildStartFragmentAction implements StartFragmentAction {

        private final FragmentManager mFragmentManager;
        private final int mContainerViewId;
        private final int mStartType;
        private final boolean mAllowingStateLoss;
        private final String mTag;

        BuildStartFragmentAction(@NonNull FragmentManager fragmentManager,
                                 @IdRes int containerViewId, int startType, boolean allowingStateLoss, String tag) {
            mFragmentManager = fragmentManager;
            mContainerViewId = containerViewId;
            mStartType = startType;
            mAllowingStateLoss = allowingStateLoss;
            mTag = tag;
        }

        @Override
        public boolean startFragment(@NonNull Postcard postcard, @NonNull Bundle bundle) throws ActivityNotFoundException, SecurityException {
            String fragmentClassName = postcard.getStringField(FragmentTransactionHandler.FRAGMENT_CLASS_NAME);
            if (TextUtils.isEmpty(fragmentClassName)) {
                RLogUtils.e("FragmentTransactionHandler.handleInternal()应返回的带有ClassName");
                return false;
            }
            if (mContainerViewId == 0) {
                RLogUtils.e("FragmentTransactionHandler.handleInternal()需要mContainerViewId");
                return false;
            }

            try {
                Fragment fragment = Fragment.instantiate(postcard.getContext(), fragmentClassName, bundle);
                if (fragment == null) {
                    return false;
                }


                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                switch (mStartType) {
                    case TYPE_ADD:
                        transaction.add(mContainerViewId, fragment, mTag);
                        break;
                    case TYPE_REPLACE:
                        transaction.replace(mContainerViewId, fragment, mTag);
                        break;
                }
                if (mAllowingStateLoss) {
                    transaction.commitAllowingStateLoss();
                } else {
                    transaction.commit();
                }
                return true;
            } catch (Exception e) {
                RLogUtils.e("FragmentTransactionPostcard", e);
                return false;
            }
        }
    }
}
