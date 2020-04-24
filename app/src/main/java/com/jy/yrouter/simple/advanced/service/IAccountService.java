package com.jy.yrouter.simple.advanced.service;

import android.content.Context;

public interface IAccountService {
    boolean isLogin();

    void startLogin(Context context);

    void notifyLoginSuccess();

    void notifyLogout();

    void registerObserver(Observer observer);

    void unregisterObserver(Observer observer);

    interface Observer {

        void onLoginSuccess();

        void onLogout();
    }
}
