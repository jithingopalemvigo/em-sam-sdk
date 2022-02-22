package com.begini.androidsdkv2.workerutil

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import org.json.JSONArray
import org.json.JSONObject

class PermissionUtil {
    companion object {
        var requirePermissionList = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_WIFI_STATE,
//            Manifest.permission.BLUETOOTH_CONNECT,
//            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.READ_CALL_LOG,
//            Manifest.permission.INTERNET
        )
    }


    fun getAllowedPermissionListForProfileUtil(context: Context): JSONObject {

        var allowedPermissionList = JSONArray()
        var deniedPermissionList =JSONArray()
        for (permission in requirePermissionList) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED
            ) allowedPermissionList.put(permission.replace("android.permission.",""))
            else deniedPermissionList.put(permission.replace("android.permission.",""))
        }
        var result = JSONObject()
        result.put("granted_permissions", allowedPermissionList)
        result.put("denied_permissions", deniedPermissionList)
        return result
    }

    fun getDeniedPermissionList(context: Context): Array<String> {

        var allowedPermissionList = arrayListOf<String>()
        var deniedPermissionList = arrayListOf<String>()
        for (permission in requirePermissionList) {
            if (ActivityCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED
            ) allowedPermissionList.add(permission.replace("android.permission.",""))
            else deniedPermissionList.add(permission.replace("android.permission.",""))
        }

        return deniedPermissionList.toTypedArray()
    }


}