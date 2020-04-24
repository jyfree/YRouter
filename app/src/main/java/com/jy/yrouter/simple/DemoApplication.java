package com.jy.yrouter.simple;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.jy.yrouter.Router;
import com.jy.yrouter.common.DefaultChainedHandler;
import com.jy.yrouter.components.DefaultOnCompleteListener;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRouter(this);
    }

    @SuppressLint("StaticFieldLeak")
    private void initRouter(Context context) {
        // 创建chainedHandler
        DefaultChainedHandler chainedHandler = new DefaultChainedHandler(context);

        // 设置全局跳转完成监听器，可用于跳转失败时统一弹Toast提示，做埋点统计等。
        chainedHandler.setGlobalOnCompleteListener(DefaultOnCompleteListener.INSTANCE);

        // 初始化
        Router.init(chainedHandler);
        //没有使用插件时，必须调用此方法
        Router.initServiceLoaderList(this.getPackageCodePath());
    }
}
