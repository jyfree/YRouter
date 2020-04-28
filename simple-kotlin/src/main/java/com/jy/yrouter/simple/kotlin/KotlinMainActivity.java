package com.jy.yrouter.simple.kotlin;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jy.yrouter.Router;
import com.jy.yrouter.appbaselibrary.DemoConstant;

/**
 * @description kotlin用法Demo
 * @date: 2020/4/22 15:58
 * @author: jy
 */
public class KotlinMainActivity extends AppCompatActivity {

    public static final String[] URIS = {

            // Kotlin
            DemoConstant.KOTLIN,

            // 自定义Scheme、Host测试；外部跳转测试
            DemoConstant.DEMO_SCHEME + "://" + DemoConstant.DEMO_HOST
                    + DemoConstant.EXPORTED_PATH,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getClass().getSimpleName());
        setContentView(R.layout.activity_kotlin_main);

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
