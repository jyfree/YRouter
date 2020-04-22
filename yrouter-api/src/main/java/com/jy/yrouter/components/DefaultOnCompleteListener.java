package com.jy.yrouter.components;


import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.jy.yrouter.core.OnCompleteListener;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.utils.RLogUtils;


/**
 * @description 默认的全局 {@link OnCompleteListener} ，在跳转失败时弹Toast提示
 * @date: 2020/4/21 11:12
 * @author: jy
 */
public class DefaultOnCompleteListener implements OnCompleteListener {

    public static final DefaultOnCompleteListener INSTANCE = new DefaultOnCompleteListener();

    @Override
    public void onSuccess(@NonNull Postcard postcard) {

    }

    @Override
    public void onError(@NonNull Postcard postcard, int resultCode) {
        String text = postcard.getStringField(Postcard.FIELD_ERROR_MSG, null);
        if (TextUtils.isEmpty(text)) {
            switch (resultCode) {
                case CODE_NOT_FOUND:
                    text = "不支持的跳转链接";
                    break;
                case CODE_FORBIDDEN:
                    text = "没有权限";
                    break;
                default:
                    text = "跳转失败";
                    break;
            }
        }
        text += "(" + resultCode + ")";
        if (RLogUtils.SHOW_LOG) {
            text += "\n" + postcard.getUri().toString();
        }
        Toast.makeText(postcard.getContext(), text, Toast.LENGTH_LONG).show();
    }
}
