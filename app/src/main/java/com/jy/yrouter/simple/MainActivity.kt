package com.jy.yrouter.simple

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jy.yrouter.BindViewTools
import com.jy.yrouter.annotation.BindView

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tv)
    var mTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BindViewTools.bind(this)
        mTextView?.text = "MainActivity bind TextView success"
    }

    fun goToTest(v: View) {
        val intent = Intent()
        intent.setClass(this, TestActivity::class.java)
        startActivity(intent)
    }
}
