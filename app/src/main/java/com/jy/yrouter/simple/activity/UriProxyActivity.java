package com.jy.yrouter.simple.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jy.yrouter.common.DefaultPostcard;
import com.jy.yrouter.core.OnCompleteListener;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.simple.base.BaseActivity;
import com.jy.yrouter.utils.RLogUtils;


/**
 * @description 接收所有外部跳转的ProxyActivity
 * @date: 2020/4/22 15:55
 * @author: jy
 */
public class UriProxyActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RLogUtils.i("外部跳转--UriProxyActivity");
        DefaultPostcard.startFromProxyActivity(this, new OnCompleteListener() {
            @Override
            public void onSuccess(@NonNull Postcard postcard) {
                finish();
            }

            @Override
            public void onError(@NonNull Postcard postcard, int resultCode) {
                finish();
            }
        });
    }
}
