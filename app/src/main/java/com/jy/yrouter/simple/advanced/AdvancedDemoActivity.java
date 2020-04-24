package com.jy.yrouter.simple.advanced;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.jy.yrouter.Router;
import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.simple.R;
import com.jy.yrouter.simple.base.BaseActivity;
import com.jy.yrouter.simple.constant.DemoConstant;

/**
 * @description 高级示例
 * @date: 2020/4/23 16:02
 * @author: jy
 */
@RouterUri(path = DemoConstant.ADVANCED_DEMO)
public class AdvancedDemoActivity extends BaseActivity {

    public static final String[] URIS = {

            // Interceptor测试
            DemoConstant.ACCOUNT_WITH_LOGIN_INTERCEPTOR,

            // UriHandler显示Toast
            DemoConstant.SHOW_TOAST_HANDLER,

            // 根据AB策略跳转不同页面
            DemoConstant.HOME_AB_TEST,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout container = findViewById(R.id.layout_container);
        for (final String uri : URIS) {
            Button button = new Button(this);
            button.setAllCaps(false);
            button.setText(uri);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jump(uri);
                }
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            container.addView(button, params);
        }
    }

    private void jump(String uri) {
        Router.startUri(this, uri);
    }
}
