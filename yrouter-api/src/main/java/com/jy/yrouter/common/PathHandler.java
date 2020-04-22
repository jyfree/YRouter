package com.jy.yrouter.common;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Interceptor;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.PostcardHandler;
import com.jy.yrouter.utils.CaseInsensitiveNonNullMap;
import com.jy.yrouter.utils.PostcardTargetTools;
import com.jy.yrouter.utils.RLogUtils;
import com.jy.yrouter.utils.RouterUtils;

import java.util.Map;

/**
 * @description 根据path分发URI给子节点，支持注册的子节点包括ActivityClassName, ActivityClass, UriHandler
 * @date: 2020/4/21 17:37
 * @author: jy
 */
public class PathHandler extends PostcardHandler {

    /**
     * path --> UriHandler
     */
    @NonNull
    private final CaseInsensitiveNonNullMap<PostcardHandler> mMap = new CaseInsensitiveNonNullMap<>();
    @Nullable
    private String mPathPrefix;
    @Nullable
    private PostcardHandler mDefaultHandler = null;

    /**
     * 设置path前缀
     */
    public void setPathPrefix(@Nullable String pathPrefix) {
        mPathPrefix = pathPrefix;
    }

    /**
     * 设置默认的ChildHandler。如果注册的ChildHandler不能处理，则使用默认ChildHandler处理。
     */
    public PathHandler setDefaultChildHandler(@NonNull PostcardHandler handler) {
        mDefaultHandler = handler;
        return this;
    }

    /**
     * 注册一个子节点
     *
     * @param path         path
     * @param target       支持ActivityClassName、ActivityClass、UriHandler
     * @param exported     是否允许外部跳转
     * @param interceptors 要添加的interceptor
     */
    public void register(String path, Object target, boolean exported, Interceptor... interceptors) {
        if (!TextUtils.isEmpty(path)) {
            path = RouterUtils.appendSlash(path);
            PostcardHandler parse = PostcardTargetTools.parse(target, exported, interceptors);
            PostcardHandler prev = mMap.put(path, parse);
            if (prev != null) {
                RLogUtils.e("[%s] 重复注册path='%s'的PostcardHandler: %s, %s", this, path, prev, parse);
            }
        }
    }

    /**
     * 注册一个子Handler
     *
     * @param path         path
     * @param handler      支持ActivityClassName、ActivityClass、UriHandler；exported默认为false
     * @param interceptors 要添加的interceptor
     */
    public void register(String path, Object handler, Interceptor... interceptors) {
        register(path, handler, false, interceptors);
    }

    /**
     * 注册若干个子Handler
     */
    public void registerAll(Map<String, Object> map) {
        if (map != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                register(entry.getKey(), entry.getValue());
            }
        }
    }

    private PostcardHandler getChild(@NonNull Postcard postcard) {
        String path = postcard.getUri().getPath();
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        path = RouterUtils.appendSlash(path);
        if (TextUtils.isEmpty(mPathPrefix)) {
            return mMap.get(path);
        }
        if (path.startsWith(mPathPrefix)) {
            return mMap.get(path.substring(mPathPrefix.length()));
        }
        return null;
    }

    @Override
    protected boolean shouldHandle(@NonNull Postcard postcard) {
        return mDefaultHandler != null || getChild(postcard) != null;
    }

    @Override
    protected void handleInternal(@NonNull final Postcard postcard,
                                  @NonNull final InterceptCallback callback) {
        PostcardHandler h = getChild(postcard);
        if (h != null) {
            h.handle(postcard, new InterceptCallback() {
                @Override
                public void onNext() {
                    handleByDefault(postcard, callback);
                }

                @Override
                public void onComplete(int resultCode) {
                    callback.onComplete(resultCode);
                }
            });
        } else {
            handleByDefault(postcard, callback);
        }
    }

    private void handleByDefault(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        PostcardHandler defaultHandler = mDefaultHandler;
        if (defaultHandler != null) {
            defaultHandler.handle(postcard, callback);
        } else {
            callback.onNext();
        }
    }
}
