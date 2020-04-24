package com.jy.yrouter.simple.activity;


import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.simple.base.BaseActivity;
import com.jy.yrouter.simple.constant.DemoConstant;

/**
 * @description uri scheme示例
 * @date: 2020/4/23 16:03
 * @author: jy
 */
@RouterUri(
        scheme = DemoConstant.DEMO_SCHEME,
        host = DemoConstant.DEMO_HOST,
        path = DemoConstant.EXPORTED_PATH,
        exported = true
)
public class ExportedActivity extends BaseActivity {

}
