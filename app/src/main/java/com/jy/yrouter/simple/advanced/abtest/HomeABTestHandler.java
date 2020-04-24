package com.jy.yrouter.simple.advanced.abtest;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.jy.yrouter.activity.AbsActivityHandler;
import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.appbaselibrary.DemoConstant;
import com.jy.yrouter.core.Postcard;


/**
 * @description 自定义handler继承于AbsActivityHandler
 * @date: 2020/4/24 10:36
 * @author: jy
 */
@RouterUri(path = DemoConstant.HOME_AB_TEST)
public class HomeABTestHandler extends AbsActivityHandler {

    @NonNull
    @Override
    protected Intent createIntent(@NonNull Postcard postcard) {
        if (getHomeABStrategy().equals("A")) {
            return new Intent(postcard.getContext(), HomeActivityA.class);
        } else {
            return new Intent(postcard.getContext(), HomeActivityB.class);
        }
    }

    public String getHomeABStrategy() {
        return Math.random() > 0.5 ? "A" : "B";
    }

}
