package com.emsdk.datasdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.begini.androidsdkv2.util.replaceFragment
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.core.BeginiDataSdkOptions
import com.begini.androidsdkv2.ui.base.BaseActivity
import com.begini.androidsdkv2.ui.splash.SplashFragment
import com.begini.androidsdkv2.util.BeginiConstants

class BeginiSdkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begini_sdk)
        findViewById<View>(R.id.fl_container).setOnClickListener {
            startActivity(Intent(this,BaseActivity::class.java))
        }
    }
}