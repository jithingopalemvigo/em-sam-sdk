package com.lib.sdksample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.begini.androidsdkv2.ui.base.BaseActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btnStartSdk).setOnClickListener{
            startActivity(Intent(this,BaseActivity::class.java))
        }
    }
}