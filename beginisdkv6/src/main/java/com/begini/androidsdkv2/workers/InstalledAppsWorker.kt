package com.begini.androidsdkv2.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.begini.androidsdk.model.*
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.core.ResultConstants
import com.begini.androidsdkv2.model.devicedata.InstalledAppInfoModel
import com.begini.androidsdkv2.model.network.ResultToUploadModel
import com.begini.androidsdkv2.util.BeginiConstants
import com.begini.androidsdkv2.util.Timber
import com.begini.androidsdkv2.workerutil.AppListInfoUtil
import com.begini.androidsdkv2.workerutil.FileUtils
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


private const val TAG = "InstalledAppsWorker"
class InstalledAppsWorker(ctx: Context, params: WorkerParameters ) : Worker(ctx, params) {
    override fun doWork(): Result {
        return try {
            val appContext = applicationContext
            makeStatusNotification("InstalledAppsWorker", appContext)

            Timber.d("Starting InstalledAppsWorker")
            val isOptionEnabled = BeginiApp.instance.getBeginiDataSdkOptions().isInstalledAppsEnabled
            //Checking if, permission for the requested option is enabled
            val isOptionAllowedInPermission = true//always true since it does not need any extra permission

            // Only send information if both conditions are satisfied
            if (isOptionEnabled!! && isOptionAllowedInPermission) {
                val output = collectDataSdkItem()
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to output)
                Timber.d("InstalledAppsWorker collection successful")
                //setting result to Begini instance
                BeginiApp.instance.dataSdkResult.INSTALLED_APPS= ResultConstants.RESULT_SUCCESS
                Result.success(outputData)
            } else {

                //setting result to Begini instance
                if(!isOptionEnabled)
                {
                    BeginiApp.instance.dataSdkResult.INSTALLED_APPS= ResultConstants.RESULT_FAILED_OPTION_DISABLED
                }else if(isOptionAllowedInPermission){
                    BeginiApp.instance.dataSdkResult.INSTALLED_APPS= ResultConstants.RESULT_FAILED_PERMISSION_DENIED
                }
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
                Result.success(outputData)
            }
        } catch (throwable: Throwable) {
            //setting result to Begini instance
            BeginiApp.instance.dataSdkResult.INSTALLED_APPS= ResultConstants.RESULT_FAILED_EXCEPTION
            Timber.d("Failure on InstalledAppsWorker")
            throwable.printStackTrace()
            val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
            Result.success(outputData)
        }
    }

    private fun collectDataSdkItem(): String {

        //create instance of moshi for converting the result file to json.
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        /*  Here we are handling the resut with a generic class.
            So specify the type of data which is trying to convert with JsonAdapter.*/
        val type =
            Types.newParameterizedType(ResultToUploadModel::class.java, MutableList::class.java)
        val adapter = moshi.adapter<ResultToUploadModel<MutableList<InstalledAppInfoModel>>>(type)
        val resultFromUtil =AppListInfoUtil().getallapps(applicationContext)
        val result = ResultToUploadModel<MutableList<InstalledAppInfoModel>>(
            type = "installed_apps",
            integrationId = "",
            uid = "",
            details = resultFromUtil
        )
        //convert the result model class to String in Json format
        val  finalResultJson= adapter.toJson(result)
        //save the file to local
        var fileUtil = FileUtils()
        fileUtil.saveJsonResultToFile(
            applicationContext,
            finalResultJson,
            BeginiConstants.FILE_NAME_INSTALLED_APPS
        )
        return BeginiConstants.FILE_NAME_INSTALLED_APPS
    }
}
