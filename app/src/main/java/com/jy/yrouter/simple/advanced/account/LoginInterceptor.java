package com.jy.yrouter.simple.advanced.account;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.jy.yrouter.core.InterceptCallback;
import com.jy.yrouter.core.Interceptor;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.core.ResultCode;
import com.jy.yrouter.simple.advanced.service.DemoServiceManager;
import com.jy.yrouter.simple.advanced.service.IAccountService;


/**
 * @description 登录拦截器
 * @date: 2020/4/24 11:08
 * @author: jy
 */
public class LoginInterceptor implements Interceptor {

    @Override
    public void intercept(@NonNull Postcard postcard, @NonNull InterceptCallback callback) {
        IAccountService accountService = DemoServiceManager.getAccountService();
        if (accountService.isLogin()) {
            callback.onNext();
        } else {
            registerObserver(accountService, callback);
            Toast.makeText(postcard.getContext(), "请先登录~", Toast.LENGTH_SHORT).show();
            DemoServiceManager.getAccountService().startLogin(postcard.getContext());

        }
    }

    private void registerObserver(final IAccountService accountService, final InterceptCallback callback) {
        accountService.registerObserver(new IAccountService.Observer() {
            @Override
            public void onLoginSuccess() {
                accountService.unregisterObserver(this);
                callback.onNext();
            }

            @Override
            public void onLogout() {
                accountService.unregisterObserver(this);
                callback.onComplete(ResultCode.CODE_ERROR);
            }
        });
    }
}
