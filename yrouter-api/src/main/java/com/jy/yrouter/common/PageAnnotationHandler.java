package com.jy.yrouter.common;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.jy.yrouter.annotation.RouterPage;
import com.jy.yrouter.components.RouterComponents;
import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.ResultCode;
import com.jy.yrouter.utils.LazyInitHelper;
import com.jy.yrouter.utils.RouterUtils;


/**
 * @description 内部页面跳转，由注解 {@link RouterPage} 配置。
 * {@link PageAnnotationHandler} 处理且只处理所有格式为 YRouter://page/* 的URI，根据path匹配，
 * 匹配不到的分发给 {@link NotFoundHandler} ，返回 {@link ResultCode#CODE_NOT_FOUND}
 * @date: 2020/4/21 17:50
 * @author: jy
 */
public class PageAnnotationHandler extends PathHandler {

    public static final String SCHEME = "YRouter";
    public static final String HOST = "page";
    public static final String SCHEME_HOST = RouterUtils.schemeHost(SCHEME, HOST);

    public static boolean isPageJump(Intent intent) {
        return intent != null && SCHEME_HOST.equals(RouterUtils.schemeHost(intent.getData()));
    }

    private final LazyInitHelper mInitHelper = new LazyInitHelper("PageAnnotationHandler") {
        @Override
        protected void doInit() {
            initAnnotationConfig();
        }
    };

    public PageAnnotationHandler() {
        addInterceptor(NotExportedInterceptor.INSTANCE); // exported全为false
        setDefaultChildHandler(NotFoundHandler.INSTANCE); // 找不到直接终止分发
    }

    /**
     * @see LazyInitHelper#lazyInit()
     */
    public void lazyInit() {
        mInitHelper.lazyInit();
    }

    protected void initAnnotationConfig() {
        RouterComponents.loadAnnotation(this, IPageAnnotationInit.class);
    }

    @Override
    public void handle(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        mInitHelper.ensureInit();
        super.handle(postcard, callback);
    }

    @Override
    protected boolean shouldHandle(@NonNull Postcard postcard) {
        return SCHEME_HOST.matches(postcard.schemeHost());
    }

    @Override
    public String toString() {
        return "PageAnnotationHandler";
    }
}