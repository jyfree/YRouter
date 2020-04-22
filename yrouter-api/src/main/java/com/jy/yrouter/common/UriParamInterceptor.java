package com.jy.yrouter.common;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Interceptor;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.utils.RouterUtils;

import java.util.Map;

/**
 * @description 给uri添加参数。{@link Postcard} 可设置 {@link UriParamInterceptor#FIELD_URI_APPEND_PARAMS}, 会被自动添加到uri中。
 * @date: 2020/4/21 17:38
 * @author: jy
 */
public class UriParamInterceptor implements Interceptor {

    /**
     * {@link Postcard} 的额外参数，Map&lt;String, String&gt;类型，跳转WebView附加额外参数
     */
    public static final String FIELD_URI_APPEND_PARAMS = "com.jy.yrouter.common.UriParamInterceptor.uri_append_params";

    @Override
    public void intercept(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        appendParams(postcard);
        callback.onNext();
    }

    @SuppressWarnings("unchecked")
    protected void appendParams(@NonNull Postcard postcard) {
        final Map<String, String> extra = postcard.getField(
                Map.class, UriParamInterceptor.FIELD_URI_APPEND_PARAMS);
        if (extra != null) {
            Uri uri = RouterUtils.appendParams(postcard.getUri(), extra);
            postcard.setUri(uri);
        }
    }
}
