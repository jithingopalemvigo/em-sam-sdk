package com.lib.sdksample

import android.app.Application
import com.emsdk.datasdk.BeginiApplication

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        BeginiApplication().onCreate()
    }
}