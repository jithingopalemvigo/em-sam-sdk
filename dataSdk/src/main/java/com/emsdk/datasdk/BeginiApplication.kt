package com.emsdk.datasdk

import android.app.Application
import com.begini.androidsdkv2.BeginiApp

class BeginiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BeginiApp().onCreate()
    }
}