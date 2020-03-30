package com.jy.yrouter.simple;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jy.yrouter.BindViewTools;
import com.jy.yrouter.annotation.BindView;

public class TestActivity extends AppCompatActivity {
    @BindView(R.id.tv)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        BindViewTools.bind(this);
        if (null != mTextView) {
            mTextView.setText("java apt bind TextView success");
        }
    }
}
