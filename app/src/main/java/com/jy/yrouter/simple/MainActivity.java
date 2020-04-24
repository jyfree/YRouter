package com.jy.yrouter.simple;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jy.yrouter.Router;
import com.jy.yrouter.appbaselibrary.DemoConstant;
import com.jy.yrouter.common.DefaultPostcard;
import com.jy.yrouter.core.OnCompleteListener;
import com.jy.yrouter.core.Postcard;
import com.jy.yrouter.simple.activity.TestUriRequestActivity;
import com.jy.yrouter.simple.base.BaseActivity;

/**
 * @description 基本用法Demo
 * @date: 2020/4/22 15:58
 * @author: jy
 */
public class MainActivity extends BaseActivity {

    public static final String[] URIS = {
            // 基本页面跳转，支持不配置Scheme、Host，支持多个path
            DemoConstant.JUMP_ACTIVITY_1,
            DemoConstant.JUMP_ACTIVITY_2,

            // Kotlin
            DemoConstant.KOTLIN,

            // request跳转测试
            DemoConstant.JUMP_WITH_REQUEST,

            // 自定义Scheme、Host测试；外部跳转测试
            DemoConstant.DEMO_SCHEME + "://" + DemoConstant.DEMO_HOST
                    + DemoConstant.EXPORTED_PATH,

            // 拨打电话
            DemoConstant.TEL,

            // 降级策略
            DemoConstant.TEST_NOT_FOUND,

            // Fragment test
            DemoConstant.JUMP_FRAGMENT_ACTIVITY,

            // Fragment to Fragment test
            DemoConstant.TEST_FRAGMENT_TO_FRAGMENT_ACTIVITY,

            // 高级Demo页面
            DemoConstant.ADVANCED_DEMO,
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
        if (DemoConstant.JUMP_WITH_REQUEST.equals(uri)) {
            new DefaultPostcard(this, uri)
                    .activityRequestCode(100)
                    .putExtra(TestUriRequestActivity.INTENT_TEST_INT, 1)
                    .putExtra(TestUriRequestActivity.INTENT_TEST_STR, "str")
                    .overridePendingTransition(R.anim.enter_activity, R.anim.exit_activity)
                    .onComplete(new OnCompleteListener() {
                        @Override
                        public void onSuccess(@NonNull Postcard postcard) {
                            Toast.makeText(postcard.getContext(), "跳转成功", Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onError(@NonNull Postcard postcard, int resultCode) {

                        }
                    })
                    .start();
        } else {
            Router.startUri(this, uri);
        }
    }
}
