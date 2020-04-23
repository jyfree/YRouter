package com.jy.yrouter.simple.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.simple.R;
import com.jy.yrouter.simple.base.BaseActivity;
import com.jy.yrouter.simple.constant.DemoConstant;

/**
 * @description 参数传递示例
 * @date: 2020/4/23 11:14
 * @author: jy
 */
@RouterUri(path = DemoConstant.JUMP_WITH_REQUEST)
public class TestUriRequestActivity extends BaseActivity {

    public static final String INTENT_TEST_INT = "test_int";
    public static final String INTENT_TEST_STR = "test_str";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        TextView text = findViewById(R.id.text);

        Intent intent = getIntent();
        StringBuilder s = new StringBuilder();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            for (String key : extras.keySet()) {
                s.append(key).append(" = ").append(extras.get(key)).append('\n');
            }
        }
        text.setText(s.toString());
    }
}
