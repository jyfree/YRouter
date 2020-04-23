package com.jy.yrouter.simple.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.simple.R;
import com.jy.yrouter.simple.base.BaseActivity;
import com.jy.yrouter.simple.constant.DemoConstant;


@RouterUri(path = DemoConstant.JUMP_FRAGMENT_ACTIVITY)
public class FragmentDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        launchFragment();
    }

    private void launchFragment() {
        Fragment fragment = Demo2Fragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
                fragment).commit();
    }
}
