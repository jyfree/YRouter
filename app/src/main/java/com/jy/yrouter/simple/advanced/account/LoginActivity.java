package com.jy.yrouter.simple.advanced.account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.appbaselibrary.DemoConstant;
import com.jy.yrouter.simple.R;
import com.jy.yrouter.simple.advanced.service.DemoServiceManager;
import com.jy.yrouter.simple.base.BaseActivity;

/**
 * @description 登录页
 * @date: 2020/4/24 11:09
 * @author: jy
 */
@RouterUri(path = DemoConstant.LOGIN)
public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                DemoServiceManager.getAccountService().notifyLoginSuccess();
                finish();
            }
        });
    }
}
