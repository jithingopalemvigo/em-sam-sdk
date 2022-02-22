package com.begini.androidsdkv2

import android.Manifest
import android.app.Application
import com.begini.androidsdkv2.core.BeginiDataSdkOptions
import com.begini.androidsdkv2.core.BeginiDataSdkResult
import com.begini.androidsdkv2.workerutil.PermissionUtil

class BeginiApp : Application() {

    lateinit var dataSdkOptions: BeginiDataSdkOptions
    lateinit var dataSdkResult: BeginiDataSdkResult

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setBeginiDataSdkOptions(beginiDataSdkOptions: BeginiDataSdkOptions) {
        this.dataSdkOptions = beginiDataSdkOptions
        var permissionList: MutableList<String> = arrayListOf()
        permissionList.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (dataSdkOptions.isBluetoothEnabled) permissionList.add(Manifest.permission.BLUETOOTH)
        if (dataSdkOptions.isContactsEnabled) permissionList.add(Manifest.permission.READ_CONTACTS)
        if (dataSdkOptions.isCalendarEnabled) permissionList.add(Manifest.permission.READ_CALENDAR)
        if (dataSdkOptions.isSmsEnabled) permissionList.add(Manifest.permission.READ_SMS)
        if (dataSdkOptions.isCallsEnabled) permissionList.add(Manifest.permission.READ_CALL_LOG)
        if (dataSdkOptions.isLocationEnabled) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        PermissionUtil.requirePermissionList = permissionList.toTypedArray()
    }


    fun getBeginiDataSdkOptions(): BeginiDataSdkOptions {
        return this.dataSdkOptions
    }

    companion object {
        lateinit var instance: BeginiApp
    }
}