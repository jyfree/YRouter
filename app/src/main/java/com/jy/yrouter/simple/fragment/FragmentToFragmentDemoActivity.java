package com.jy.yrouter.simple.fragment;

import android.os.Bundle;

import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.common.PageAnnotationHandler;
import com.jy.yrouter.fragment.FragmentTransactionPostcard;
import com.jy.yrouter.simple.R;
import com.jy.yrouter.simple.base.BaseActivity;
import com.jy.yrouter.simple.constant.DemoConstant;


/**
 * @description fragment to fragment 示例
 * @date: 2020/4/23 16:06
 * @author: jy
 */
@RouterUri(path = DemoConstant.TEST_FRAGMENT_TO_FRAGMENT_ACTIVITY)
public class FragmentToFragmentDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        launchFragment();
    }

    private void launchFragment() {
        new FragmentTransactionPostcard(this, PageAnnotationHandler.SCHEME_HOST + DemoConstant.TEST_DEMO_FRAGMENT_1)
                .add(R.id.fragment_container)
                .allowingStateLoss()
                .start();
    }

}
