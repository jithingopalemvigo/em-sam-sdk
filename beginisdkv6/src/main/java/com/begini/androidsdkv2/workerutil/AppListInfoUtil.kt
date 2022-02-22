package com.begini.androidsdkv2.workerutil
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import com.begini.androidsdkv2.model.devicedata.InstalledAppInfoModel


class AppListInfoUtil {
    fun getallapps(context: Context): MutableList<InstalledAppInfoModel> {
        // get list of all the apps installed
        val packageInfos: List<PackageInfo> =
            context.getPackageManager().getInstalledPackages(PackageManager.GET_META_DATA)
        // create a list with size of total number of apps
        val apps = mutableListOf<InstalledAppInfoModel>()
        // add all the app name in string list
        Log.e("Jithin", "Total Number of apps " + packageInfos.size)

        for (info in packageInfos) {
            apps.add(
            InstalledAppInfoModel(
                info.firstInstallTime,
                info.lastUpdateTime,
                info.versionName,
                info.versionCode,
                info.packageName
            )
            )
        }
        return apps

    }


}