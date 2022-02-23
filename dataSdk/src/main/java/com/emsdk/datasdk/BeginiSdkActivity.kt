package com.emsdk.datasdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.begini.androidsdkv2.util.replaceFragment
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.core.BeginiDataSdkOptions
import com.begini.androidsdkv2.core.BeginiDataSdkResult
import com.begini.androidsdkv2.ui.base.BaseActivity
import com.begini.androidsdkv2.ui.splash.SplashFragment
import com.begini.androidsdkv2.util.BeginiConstants

class BeginiSdkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begini_sdk)
        findViewById<View>(R.id.fl_container).setOnClickListener {

            var intent= Intent(this, BaseActivity::class.java)
            val beginiDataSdkOptions = BeginiDataSdkOptions(
                u_id="testemgigo",
                integration_id="61ebc61a40d78bbca587d6c0",
                api_key="a6d1c57a-7a57-40a3-a36e-9c5ccc7a9802",
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
            intent.putExtra("OPTIONS",beginiDataSdkOptions)
            requestBeginiActivityForResult.launch(intent)
        }
        }
    private val requestBeginiActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            Log.e("Jithin", "Result success")
            var result =
                (activityResult.data?.extras?.getSerializable("DATA") as BeginiDataSdkResult)

            Log.e("Jithin", "Result = \n" +
                    "BATTERY "+result.BATTERY+"\n"+
                    "BLUETOOTH "+result.BLUETOOTH+"\n"+
                    "CALENDER "+result.CALENDER+"\n"+
                    "CALL_LOG "+result.CALL_LOG+"\n"+
                    "CONTACTS "+result.CONTACTS+"\n"+
                    "DISK_WRITE "+result.DISK_WRITE+"\n"+
                    "GALLERY_EXIF "+result.GALLERY_EXIF+"\n"+
                    "INSTALLED_APPS "+result.INSTALLED_APPS+"\n"+
                    "LOCATION "+result.LOCATION+"\n"+
                    "MEDIA_METADATA "+result.MEDIA_METADATA+"\n"+
                    "DISK_WRITE "+result.DISK_WRITE+"\n"+
                    "PROFILE "+result.PROFILE+"\n"+
                    "SERVER_SYNC "+result.SERVER_SYNC+"\n"+
                    "SMS "+result.SMS+"\n"+
                    "WIFI:"+result.WIFI+"\n")


        } else if (activityResult.resultCode == RESULT_CANCELED) {
            Log.e("Jithin", "Result Canceled")

        }

    }

}