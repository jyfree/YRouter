package com.jy.yrouter.simple.advanced.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.appbaselibrary.DemoConstant;
import com.jy.yrouter.simple.R;
import com.jy.yrouter.simple.advanced.service.DemoServiceManager;
import com.jy.yrouter.simple.base.BaseActivity;

/**
 * @description 用户账户页，需要先登录
 * @date: 2020/4/24 11:07
 * @author: jy
 */
@RouterUri(path = DemoConstant.ACCOUNT_WITH_LOGIN_INTERCEPTOR,
        interceptors = LoginInterceptor.class)
public class UserAccountActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的账户");
        setContentView(R.layout.activity_account);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoServiceManager.getAccountService().notifyLogout();
                finish();
            }
        });
    }
}
