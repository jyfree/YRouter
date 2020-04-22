package com.jy.yrouter.fragment;

import android.content.Context;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;


/**
 * @description Fragment路由跳转基类
 * @date: 2020/4/21 10:39
 * @author: jy
 */
public abstract class AbsFragmentTransactionPostcard extends AbsFragmentPostcard {

    protected final static int TYPE_ADD = 1;
    protected final static int TYPE_REPLACE = 2;

    protected int mType = TYPE_ADD;
    protected int mContainerViewId;
    protected boolean mAllowingStateLoss;
    protected String mTag;

    public AbsFragmentTransactionPostcard(@NonNull Context context, String uri) {
        super(context, uri);
    }

    /**
     * 在containerViewId上添加指定的Fragment
     *
     * @param containerViewId 容器ID
     * @return this
     */
    public AbsFragmentTransactionPostcard add(@IdRes int containerViewId) {
        mContainerViewId = containerViewId;
        mType = TYPE_ADD;
        return this;
    }

    /**
     * 在containerViewId上替换指定的Fragment
     *
     * @param containerViewId 容器ID
     * @return this
     */
    public AbsFragmentTransactionPostcard replace(@IdRes int containerViewId) {
        mContainerViewId = containerViewId;
        mType = TYPE_REPLACE;
        return this;
    }

    /**
     * 指定tag
     *
     * @param tag 指定tag
     * @return this
     */
    public AbsFragmentTransactionPostcard tag(String tag) {
        mTag = tag;
        return this;
    }

    /**
     * 允许状态丢失的提交
     *
     * @return this
     */
    public AbsFragmentTransactionPostcard allowingStateLoss() {
        mAllowingStateLoss = true;
        return this;
    }


}
