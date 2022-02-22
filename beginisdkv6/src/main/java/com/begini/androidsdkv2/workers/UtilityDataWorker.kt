package com.begini.androidsdkv2.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.begini.androidsdk.model.makeStatusNotification
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.util.BeginiConstants
import com.begini.androidsdkv2.util.Timber


private const val TAG = "UtilityDataWorker"
class UtilityDataWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        return try {
            val appContext = applicationContext
            makeStatusNotification("UtilityDataWorker", appContext)

            Timber.d("Starting UtilityDataWorker")
            val isOptionEnabled = BeginiApp.instance.getBeginiDataSdkOptions().isSmsEnabled
            //Checking if, permission for the requested option is enabled
            val isOptionAllowedInPermission = true

            // Only send information if both conditions are satisfied
            if (isOptionEnabled!! && isOptionAllowedInPermission) {
                val output = collectDataSdkItem()
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to output)
                Timber.d("UtilityDataWorker collection successful")
                Result.success(outputData)
            } else {
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
                Result.success(outputData)
            }
        } catch (throwable: Throwable) {
            Timber.d("Failure on UtilityDataWorker")
            throwable.printStackTrace()
            val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
            Result.success(outputData)
        }
    }

    private fun collectDataSdkItem(): String {
        // TODO Implement collect contacts here and change the return object and you can also save the items already
        val sampleData = "{\n" +
                "\t\"utility\": [{\n" +
                "\t\t\"name\": \"Test\"\n" +
                "\t}]\n" +
                "}"

        return "text"
    }
}
