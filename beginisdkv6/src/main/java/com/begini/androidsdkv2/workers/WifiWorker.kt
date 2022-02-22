package com.begini.androidsdkv2.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.begini.androidsdk.model.isPermissionAllowed
import com.begini.androidsdk.model.makeStatusNotification
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.core.ResultConstants
import com.begini.androidsdkv2.util.BeginiConstants
import com.begini.androidsdkv2.util.Timber
import com.begini.androidsdkv2.workerutil.FileUtils
import com.begini.androidsdkv2.workerutil.WifiUtil
import org.json.JSONObject


private const val TAG = "WifiWorker"

class WifiWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        return try {
            val appContext = applicationContext
            makeStatusNotification("WifiWorker", appContext)

            Timber.d("Starting WifiWorker")
            val isOptionEnabled = BeginiApp.instance.getBeginiDataSdkOptions().isWifiEnabled
            //Checking if, permission for the requested option is enabled
            val isOptionAllowedInPermission =
                appContext.isPermissionAllowed(android.Manifest.permission.ACCESS_WIFI_STATE)

            // Only send information if both conditions are satisfied
            if (isOptionEnabled!! && isOptionAllowedInPermission) {
                val output = collectDataSdkItem()
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to output)
                Timber.d("Wifi collection successful")
                //setting result to Begini instance
                BeginiApp.instance.dataSdkResult.WIFI = ResultConstants.RESULT_SUCCESS
                Result.success(outputData)
            } else {

                //setting result to Begini instance
                if (!isOptionEnabled) {
                    BeginiApp.instance.dataSdkResult.WIFI =
                        ResultConstants.RESULT_FAILED_OPTION_DISABLED
                } else if (isOptionAllowedInPermission) {
                    BeginiApp.instance.dataSdkResult.WIFI =
                        ResultConstants.RESULT_FAILED_PERMISSION_DENIED
                }
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
                Result.success(outputData)
            }
        } catch (throwable: Throwable) {
            //setting result to Begini instance
            BeginiApp.instance.dataSdkResult.WIFI = ResultConstants.RESULT_FAILED_EXCEPTION
            Timber.d("Failure on Wifi worker")
            throwable.printStackTrace()
            val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
            Result.success(outputData)
        }
    }

    private fun collectDataSdkItem(): String {
        /*
        Since getWifiInformation already returns JSONObject,
        and the content inside that JSONObject is small size
        we are not converting the result to ResultToUploadModel
        */
        var wifiResultJson = WifiUtil().getWifiInformation(applicationContext)
        var wifiRequest = JSONObject()
        wifiRequest.put("type", "wifi")
        wifiRequest.put("integration_id", "")
        wifiRequest.put("uid", "")
        wifiRequest.put("details", wifiResultJson)
        var fileUtil = FileUtils()
        fileUtil.saveJsonResultToFile(
            applicationContext,
            wifiRequest.toString(),
            BeginiConstants.FILE_NAME_WIFI
        )
        return BeginiConstants.FILE_NAME_WIFI
    }
}
