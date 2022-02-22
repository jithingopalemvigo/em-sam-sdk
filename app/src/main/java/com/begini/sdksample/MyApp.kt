package com.begini.sdksample

import android.app.Application
import com.emsdk.datasdk.BeginiApplication

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        BeginiApplication().onCreate()
    }
}