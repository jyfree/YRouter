package com.jy.yrouter.simple.advanced.service;

import android.content.Context;

import androidx.annotation.NonNull;

import com.jy.yrouter.Router;
import com.jy.yrouter.annotation.RouterService;
import com.jy.yrouter.simple.constant.DemoConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 接口实现类
 * @date: 2020/4/24 14:53
 * @author: jy
 */
@RouterService(interfaces = IAccountService.class, key = DemoConstant.SINGLETON, singleton = true)
public class FakeAccountService implements IAccountService {

    private final List<Observer> mObservers = new ArrayList<>();

    private boolean mIsLogin = false;

    @Override
    public boolean isLogin() {
        return mIsLogin;
    }

    @Override
    public void startLogin(Context context) {
        Router.startUri(context, DemoConstant.LOGIN);
    }

    @Override
    public void notifyLoginSuccess() {
        mIsLogin = true;
        Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onLoginSuccess();
        }
    }

    @Override
    public void notifyLogout() {
        mIsLogin = false;
        Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onLogout();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(Observer observer) {
        if (observer != null) {
            mObservers.remove(observer);
        }
    }

    @NonNull
    private Observer[] getObservers() {
        return mObservers.toArray(new Observer[mObservers.size()]);
    }
}
