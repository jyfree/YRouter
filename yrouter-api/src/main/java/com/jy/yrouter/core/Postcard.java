package com.jy.yrouter.core;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jy.yrouter.Router;
import com.jy.yrouter.utils.RLogUtils;
import com.jy.yrouter.utils.RouterUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 路由容器
 * @date: 2020/4/2 11:14
 * @author: jy
 */
public class Postcard {

    /**
     * 跳转请求完成的回调
     */
    public static final String FIELD_COMPLETE_LISTENER = "com.jy.yrouter.core.CompleteListener";
    /**
     * 跳转请求的结果
     */
    public static final String FIELD_RESULT_CODE = "com.jy.yrouter.core.result";
    /**
     * 跳转请求失败信息
     */
    public static final String FIELD_ERROR_MSG = "com.jy.yrouter.core.error.msg";

    @NonNull
    private final Context mContext;
    @NonNull
    private final HashMap<String, Object> mFields;

    /**
     * 路由uri
     */
    private Uri mUri;

    private String mSchemeHost = null;
    /**
     * 是否跳过拦截器
     */
    private boolean isSkipInterceptors = false;


    public Postcard(@NonNull Context context, String uri) {
        this(context, parseUriSafely(uri), new HashMap<String, Object>());
    }

    public Postcard(@NonNull Context context, Uri uri) {
        this(context, uri, new HashMap<String, Object>());
    }

    public Postcard(@NonNull Context context, String uri, HashMap<String, Object> fields) {
        this(context, parseUriSafely(uri), fields);
    }

    public Postcard(@NonNull Context context, Uri uri, HashMap<String, Object> fields) {
        mContext = context;
        mUri = uri == null ? Uri.EMPTY : uri;
        mFields = fields == null ? new HashMap<String, Object>() : fields;
    }


    @NonNull
    public HashMap<String, Object> getFields() {
        return mFields;
    }

    private static Uri parseUriSafely(@Nullable String uri) {
        return TextUtils.isEmpty(uri) ? Uri.EMPTY : Uri.parse(uri);
    }

    public Postcard onComplete(OnCompleteListener listener) {
        putField(FIELD_COMPLETE_LISTENER, listener);
        return this;
    }

    public Postcard setResultCode(int resultCode) {
        putField(FIELD_RESULT_CODE, resultCode);
        return this;
    }

    public Postcard setErrorMessage(String message) {
        putField(FIELD_ERROR_MSG, message);
        return this;
    }


    public OnCompleteListener getOnCompleteListener() {
        return getField(OnCompleteListener.class, FIELD_COMPLETE_LISTENER);
    }

    public int getResultCode() {
        return getIntField(FIELD_RESULT_CODE, ResultCode.CODE_ERROR);
    }

    public String getErrorMessage() {
        return getStringField(FIELD_ERROR_MSG, "");
    }

    @NonNull
    public Context getContext() {
        return mContext;
    }

    /**
     * 根据scheme和host生成的字符串
     */
    public String schemeHost() {
        if (mSchemeHost == null) {
            mSchemeHost = RouterUtils.schemeHost(getUri());
        }
        return mSchemeHost;
    }

    /**
     * 判断Uri是否为空。空的Uri会在ChainedHandler中被处理，其他Handler不需要关心。
     */
    public boolean isUriEmpty() {
        return Uri.EMPTY.equals(mUri);
    }

    @NonNull
    public Uri getUri() {
        return mUri;
    }

    public void setUri(@NonNull Uri uri) {
        if (!Uri.EMPTY.equals(uri)) {
            mUri = uri;
            mSchemeHost = null;
        } else {
            RLogUtils.e("Postcard.setUri不应该传入空值");
        }
    }


    public boolean isSkipInterceptors() {
        return isSkipInterceptors;
    }

    public Postcard setSkipInterceptors(boolean skipInterceptors) {
        isSkipInterceptors = skipInterceptors;
        return this;
    }

    /**
     * 设置Extra参数
     */
    public <T> Postcard putField(@NonNull String key, T val) {
        if (val != null) {
            mFields.put(key, val);
        }
        return this;
    }

    public synchronized <T> Postcard putFieldIfAbsent(@NonNull String key, T val) {
        if (val != null) {
            if (!mFields.containsKey(key)) {
                mFields.put(key, val);
            }
        }
        return this;
    }

    public Postcard putFields(HashMap<String, Object> fields) {
        if (fields != null) {
            mFields.putAll(fields);
        }
        return this;
    }

    public boolean hasField(@NonNull String key) {
        return mFields.containsKey(key);
    }

    public int getIntField(@NonNull String key, int defaultValue) {
        return getField(Integer.class, key, defaultValue);
    }

    public long getLongField(@NonNull String key, long defaultValue) {
        return getField(Long.class, key, defaultValue);
    }

    public boolean getBooleanField(@NonNull String key, boolean defaultValue) {
        return getField(Boolean.class, key, defaultValue);
    }

    public String getStringField(@NonNull String key) {
        return getField(String.class, key, null);
    }

    public String getStringField(@NonNull String key, String defaultValue) {
        return getField(String.class, key, defaultValue);
    }

    public <T> T getField(@NonNull Class<T> clazz, @NonNull String key) {
        return getField(clazz, key, null);
    }

    public <T> T getField(@NonNull Class<T> clazz, @NonNull String key, T defaultValue) {
        Object field = mFields.get(key);
        if (field != null) {
            try {
                return clazz.cast(field);
            } catch (ClassCastException e) {
                RLogUtils.e(e.getMessage());
            }
        }
        return defaultValue;
    }

    public void start() {
        Router.startUri(this);
    }

    public String toFullString() {
        StringBuilder s = new StringBuilder(mUri.toString());
        s.append(", fields = {");
        boolean first = true;
        for (Map.Entry<String, Object> entry : mFields.entrySet()) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            s.append(entry.getKey()).append(" = ").append(entry.getValue());
        }
        s.append("}");
        return s.toString();
    }
}
