package com.jy.yrouter.simple.activity;


import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.simple.base.BaseActivity;
import com.jy.yrouter.simple.constant.DemoConstant;

@RouterUri(
        scheme = DemoConstant.DEMO_SCHEME,
        host = DemoConstant.DEMO_HOST,
        path = DemoConstant.EXPORTED_PATH,
        exported = true
)
public class ExportedActivity extends BaseActivity {

}
