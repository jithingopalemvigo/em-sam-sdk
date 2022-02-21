package com.emsdk.datasdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.begini.androidsdk.model.replaceFragment
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.core.BeginiDataSdkOptions
import com.begini.androidsdkv2.core.BeginiDataSdkResult
import com.begini.androidsdkv2.ui.splash.SplashFragment
import com.begini.androidsdkv2.util.BeginiConstants

class BeginiSdkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BeginiConstants.TAG_OUTPUT = "TAG_OUTPUT" + System.currentTimeMillis()
        setContentView(R.layout.activity_begini_sdk)
        if (intent.extras != null) {
            if(intent.extras!!.getSerializable("OPTIONS")!=null) {
                val beginiDataSdkOptions =
                    intent.extras!!.getSerializable("OPTIONS") as BeginiDataSdkOptions
                if (beginiDataSdkOptions != null)
                    BeginiApp.instance.setBeginiDataSdkOptions(beginiDataSdkOptions)
            }
        } else {
            Log.e("Extras","Is empty or null, so setting new sdk options")
            val beginiDataSdkOptions = BeginiDataSdkOptions(
                isContactsEnabled = true,
                isSmsEnabled = false,
                isCallsEnabled = false,
                isCalendarEnabled = false,
                isBatteryEnabled = true,
                isBluetoothEnabled = true,
                isWifiEnabled = true,
                isGalleryExifDataEnabled = false,
                isMediaMetadataEnabled = false,
                isProfileEnabled = true,
                isInstalledAppsEnabled = true,
                isLocationEnabled = true
            )
            BeginiApp.instance.setBeginiDataSdkOptions(beginiDataSdkOptions)
            BeginiApp.instance.dataSdkResult= BeginiDataSdkResult()
        }
        replaceFragment(SplashFragment(), "SplashScreenFragment", R.id.fl_container)


    }

    fun sendResultBack(resultType:Int,result: BeginiDataSdkResult?) {
        setResult(resultType, Intent().apply {
            putExtra("DATA", result)
        })
        finish()
    }

    override fun onBackPressed() {
        sendResultBack(RESULT_CANCELED,null)
    }
}