package com.jy.yrouter.simple.activity;


import com.jy.yrouter.annotation.RouterUri;
import com.jy.yrouter.appbaselibrary.DemoConstant;
import com.jy.yrouter.simple.base.BaseActivity;


/**
 * @description 多path示例
 * @date: 2020/4/23 11:14
 * @author: jy
 */
@RouterUri(path = {DemoConstant.JUMP_ACTIVITY_1, DemoConstant.JUMP_ACTIVITY_2})
public class TestBasicActivity extends BaseActivity {

}
