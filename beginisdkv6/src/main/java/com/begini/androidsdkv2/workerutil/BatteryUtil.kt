package com.begini.androidsdkv2.workerutil

import android.content.Context
import android.os.BatteryManager
import com.begini.androidsdkv2.model.devicedata.BatteryModel

class BatteryUtil {
    fun getBatteryInfo(context: Context): BatteryModel {
        val bm = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        // Get the battery percentage and store it in a INT variable
        val batLevel:Int = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        val isCharging = bm.isCharging
        return  BatteryModel(batLevel,isCharging,System.currentTimeMillis())
    }
}