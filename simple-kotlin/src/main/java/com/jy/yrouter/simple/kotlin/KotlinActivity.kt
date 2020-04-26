package com.jy.yrouter.simple.kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jy.yrouter.Router
import com.jy.yrouter.annotation.RouterUri
import com.jy.yrouter.appbaselibrary.DemoConstant
import kotlinx.android.synthetic.main.activity_kotlin.*

@RouterUri(path = [DemoConstant.KOTLIN])
class KotlinActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val a = Router.callMethod(DemoConstant.ADD_METHOD, 2, 3) as Int
        tv.text = "Kotlin模块调用Java模块的Service \n 2 + 3 = {$a}"
    }
}
