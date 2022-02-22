package com.begini.sdksample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.emsdk.datasdk.BeginiSdkActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btnStartSdk).setOnClickListener{
            startActivity(Intent(this,BeginiSdkActivity::class.java))

        }
    }
}