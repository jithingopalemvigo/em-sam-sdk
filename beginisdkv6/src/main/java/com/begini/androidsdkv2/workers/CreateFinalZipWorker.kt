@file:JvmName("CreateFinalZipWorker")

package com.begini.androidsdkv2.workers
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.begini.androidsdk.model.isPermissionAllowed
import com.begini.androidsdk.model.makeStatusNotification
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.core.ResultConstants
import com.begini.androidsdkv2.util.AppConstants
import com.begini.androidsdkv2.util.BeginiConstants
import com.begini.androidsdkv2.util.Timber
import com.begini.androidsdkv2.workerutil.CompressUtil
import com.begini.androidsdkv2.workerutil.FileUtils
import java.io.File


class CreateFinalZipWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    override fun doWork(): Result {
        return try {
            val appContext = applicationContext
            makeStatusNotification("CreatingFinalZip", appContext)
            //Checking if, permission for the requested option is enabled
            val isOptionAllowedInPermission =
                appContext.isPermissionAllowed(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

            if (isOptionAllowedInPermission) {
                val output = createFinalZipOutput()
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to output)
                Timber.d("CreatingFinalZip collection successful")
                BeginiConstants.isAllTasksCompleted = true

                //setting result to Begini instance
                BeginiApp.instance.dataSdkResult.DISK_WRITE = ResultConstants.RESULT_SUCCESS
                Result.success(outputData)
            } else {
                BeginiApp.instance.dataSdkResult.DISK_WRITE = ResultConstants.RESULT_FAILED_PERMISSION_DENIED
                val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
                Result.success(outputData)

            }

        } catch (throwable: Throwable) {
            Timber.d("Failure on CreatingFinalZip")
            //setting result to Begini instance
            BeginiApp.instance.dataSdkResult.DISK_WRITE = ResultConstants.RESULT_FAILED_EXCEPTION
            throwable.printStackTrace()
            val outputData = workDataOf(BeginiConstants.TAG_OUTPUT to null)
            Result.success(outputData)
        }
    }

    private fun createFinalZipOutput(): String {
        val outputFolderPath =
            applicationContext.externalCacheDir?.absolutePath + AppConstants.LOCAL_FILES.zip_folder
        if (!File(outputFolderPath).exists()) {
            File(outputFolderPath).mkdirs()
        }
        val isZipFileCreated =
            CompressUtil().zipFolder(
                applicationContext,
                outputFolderPath + AppConstants.LOCAL_FILES.zip_final_file
            )
        //delete the other files if the zip file successfully created.
        FileUtils().deleteAllGzipFiles(applicationContext)

        return BeginiConstants.DATA_SDK_CREATE_FINAL_ZIP
    }
}
