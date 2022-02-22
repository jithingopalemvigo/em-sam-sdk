
package com.begini.androidsdkv2.ui.home.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.core.BeginiDataSdkOptions
import com.begini.androidsdkv2.core.BeginiDataSdkResult
import com.begini.androidsdkv2.util.BeginiConstants
import com.begini.androidsdkv2.workers.*


class BeginiDataSdkWorkViewModel (
    private val application: Application
) : ViewModel() {
    private val workManager = WorkManager.getInstance(application)
    var outputWorkInfos: LiveData<List<WorkInfo>> =
        workManager.getWorkInfosByTagLiveData(BeginiConstants.TAG_OUTPUT)

    fun collectDataSdkItem(beginiDataSdkOptions: BeginiDataSdkOptions) {

        // IMPORTANT: Set the options
//        BeginiApp.instance.setBeginiDataSdkOptions(beginiDataSdkOptions)
//        BeginiApp.instance.dataSdkResult=BeginiDataSdkResult()
//        val contactsWorkRequest = OneTimeWorkRequest.from(ContactsWorker::class.java)
        val contactsWorkRequest =
            OneTimeWorkRequestBuilder<ContactsWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val smsWorkRequest =
            OneTimeWorkRequestBuilder<SmsWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val batteryWorkRequest =
            OneTimeWorkRequestBuilder<BatteryInfoWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val calenderWorkRequest =
            OneTimeWorkRequestBuilder<CalenderWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val mediaMetaDataWorkRequest =
            OneTimeWorkRequestBuilder<MediaMetaDataWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val profileDataWorkRequest =
            OneTimeWorkRequestBuilder<ProfileWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val bluetoothWorkRequest =
            OneTimeWorkRequestBuilder<BluetoothWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val wifiWorkRequest =
            OneTimeWorkRequestBuilder<WifiWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val callLogsWorkRequest =
            OneTimeWorkRequestBuilder<CallLogWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val locationInfoWorkRequest =
            OneTimeWorkRequestBuilder<LocationInfoWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val installedAppsWorkRequest =
            OneTimeWorkRequestBuilder<InstalledAppsWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val galleryExifWorkRequest =
            OneTimeWorkRequestBuilder<GalleryExifDataWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        val createFinalZipWorkRequest =
            OneTimeWorkRequestBuilder<CreateFinalZipWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()
        /*val utilityDataWorkRequest =
            OneTimeWorkRequestBuilder<UtilityDataWorker>().addTag(BeginiConstants.TAG_OUTPUT)
                .build()*/
        workManager.cancelAllWorkByTag(BeginiConstants.TAG_OUTPUT)
        // IMPORTANT: These are executed in sequence
        // The plan is each Work Request will already persist the single file (JSON file, to be compressed into GZIP)
        // Then a final Work Request that will call the API for uploading will be created.
        var syncChain = workManager
            .beginUniqueWork(
                System.currentTimeMillis().toString(),
                ExistingWorkPolicy.REPLACE,
                profileDataWorkRequest
            )
            .then(batteryWorkRequest)
            .then(bluetoothWorkRequest)
            .then(wifiWorkRequest)
            .then(callLogsWorkRequest)
            .then(installedAppsWorkRequest)
            .then(contactsWorkRequest)
            .then(calenderWorkRequest)
            .then(smsWorkRequest)
            .then(galleryExifWorkRequest)
            .then(mediaMetaDataWorkRequest)
            .then(locationInfoWorkRequest)
            .then(createFinalZipWorkRequest)
        syncChain.enqueue()
        outputWorkInfos = syncChain.workInfosLiveData
    }
}
