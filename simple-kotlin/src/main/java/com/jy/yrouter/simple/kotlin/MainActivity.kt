package com.jy.yrouter.simple.kotlin

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jy.yrouter.BindViewTools
import com.jy.yrouter.annotation.BindView

class MainActivity : AppCompatActivity() {

    @BindView(R.id.tv)
    lateinit var mTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BindViewTools.bind(this)
        mTextView.text = "kotlin apt bind TextView success"
    }
}
