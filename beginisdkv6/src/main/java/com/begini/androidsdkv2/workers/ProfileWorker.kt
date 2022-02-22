package com.begini.androidsdkv2.workers

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.begini.androidsdk.model.makeStatusNotification
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.core.ResultConstants
import com.begini.androidsdkv2.util.BeginiConstants
import com.begini.androidsdkv2.util.Timber
import com.begini.androidsdkv2.workerutil.FileUtils
import com.begini.androidsdkv2.workerutil.ProfileUtil
import org.json.JSONObject


private const val TAG = "ProfileWorker"

class ProfileWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {
        return try {
            val appContext = applicationContext
            makeStatusNotification("ProfileWorker", appContext)

            Timber.d("Starting ProfileWorker")
            val isOptionEnabled = BeginiApp.instance.getBeginiDataSdkOptions().isProfileEnabled
            //Checking if, permission for the requested option is enabled
            val isOptionAllowedInPermission = true//always true since no extra permission needed

            // Only send information if both conditions are satisfied
            if (isOptionEnabled!! && isOptionAllowedInPermission) {
                val output = collectDataSdkItem()
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to output)
                Timber.d("ProfileWorker collection successful")
                //setting result to Begini instance
                BeginiApp.instance.dataSdkResult.PROFILE = ResultConstants.RESULT_SUCCESS
                Result.success(outputData)
            } else {

                //setting result to Begini instance
                if (!isOptionEnabled) {
                    BeginiApp.instance.dataSdkResult.PROFILE =
                        ResultConstants.RESULT_FAILED_OPTION_DISABLED
                } else if (isOptionAllowedInPermission) {
                    BeginiApp.instance.dataSdkResult.PROFILE =
                        ResultConstants.RESULT_FAILED_PERMISSION_DENIED
                }
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
                Result.success(outputData)
            }
        } catch (throwable: Throwable) {
            //setting result to Begini instance
            BeginiApp.instance.dataSdkResult.PROFILE = ResultConstants.RESULT_FAILED_EXCEPTION
            Timber.d("Failure on ProfileWorker")
            throwable.printStackTrace()
            val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
            Result.success(outputData)
        }
    }

    private fun collectDataSdkItem(): String {

        /*
        Since getProfile already returns JSONObject,
        and the content inside that JSONObject is small size
        we are not converting the result to ResultToUploadModel
        */
        var profileDataJson = ProfileUtil().getProfile(applicationContext)
        var profileRequest = JSONObject()
        profileRequest.put("type", "profile")
        profileRequest.put("integration_id", "")
        profileRequest.put("uid", "")
        profileRequest.put("details", profileDataJson)
        var fileUtil = FileUtils()
        fileUtil.saveJsonResultToFile(
            applicationContext,
            profileRequest.toString(),
            BeginiConstants.FILE_NAME_PROFILE
        )
        return BeginiConstants.FILE_NAME_PROFILE
    }
}
