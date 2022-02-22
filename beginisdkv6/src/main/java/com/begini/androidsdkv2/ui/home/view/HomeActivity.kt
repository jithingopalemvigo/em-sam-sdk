package com.begini.androidsdkv2.ui.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.R
import com.begini.androidsdkv2.core.BeginiDataSdkOptions
import com.begini.androidsdkv2.model.network.GetNewTokenRequest
import com.begini.androidsdkv2.network.api.ApiHelper
import com.begini.androidsdkv2.network.api.RetrofitBuilder
import com.begini.androidsdkv2.network.networkutil.Status
import com.begini.androidsdkv2.network.repository.AuthRepository
import com.begini.androidsdkv2.network.viewmodel.BeginiDataSdkViewModelFactory
import com.begini.androidsdkv2.network.viewmodel.ViewModelFactory
import com.begini.androidsdkv2.ui.home.viewmodel.AuthViewModel
import com.begini.androidsdkv2.ui.home.viewmodel.BeginiDataSdkWorkViewModel
import com.begini.androidsdkv2.util.AppConstants
import com.begini.androidsdkv2.util.BeginiConstants
import com.begini.androidsdkv2.util.Timber
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var workViewModel: BeginiDataSdkWorkViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
    }

    private fun setupViewModel() {
        val apiHelper = ApiHelper(RetrofitBuilder.apiService)
        val factory = ViewModelFactory(apiHelper)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        setupAuthObservers()
        workViewModel = ViewModelProvider(
            this, BeginiDataSdkViewModelFactory(
                application
            )
        ).get(BeginiDataSdkWorkViewModel::class.java)
        workViewModel.outputWorkInfos.observe(this,workInfosObserver())
        collectDataSdkItems()
    }
    private fun workInfosObserver(): Observer<List<WorkInfo>> {

        return Observer { listOfWorkInfo ->

            // If there are no matching work info, do nothing
            if (listOfWorkInfo.isNullOrEmpty()) {
                Log.e("Work Status", "list of work info null")
                return@Observer
            }

            // We only care about the one output status.
            // Every continuation has only one worker tagged TAG_OUTPUT
            val workInfo = listOfWorkInfo[0]

            // To revise and for checking
            // Work requests are only triggered once but listeners are triggered multiple times

            // An initial revision is to check the ID of the work , and its status being finished.

            if (workInfo.state.isFinished) {
                Timber.d("showWorkFinished")
            } else {
                Timber.d("showWorkInProgress")
            }
            var completedWork = ""
            var completedWorkCount = 0
            var i = 0;
            for (workStatus in listOfWorkInfo) {
                i++
                if (workStatus.outputData != null) {
                    if (workStatus.outputData.getString(BeginiConstants.TAG_OUTPUT) != null) {
                        completedWork += "\n" + i + " " + workStatus.outputData
                        completedWork += workStatus.state.isFinished.toString()
                        completedWorkCount++
                    }

                    if (workStatus.state.isFinished && workStatus.outputData.getString(
                            BeginiConstants.TAG_OUTPUT
                        ).equals(BeginiConstants.DATA_SDK_CREATE_FINAL_ZIP)
                    ) {

                        Log.e("Work Status", "count " + completedWorkCount + "\n" + completedWork)
                        Log.e("Jithin", "Work completed")


                    }
                }
            }
            Log.e("Work Status", "count " + completedWorkCount + "\n" + completedWork)
        }
    }
    private fun collectDataSdkItems() {

        // 01 Get the web api token
        // 02 Create a session
        // 03 Get the instance of the Begini Data SDK
        // 04 Create objects needed for data collection
        // 05 Proceed to Data SDK collection
        val beginiDataSdkOptions = BeginiDataSdkOptions(
            isContactsEnabled = true,
            isSmsEnabled = true,
            isCallsEnabled = true,
            isCalendarEnabled = true,
            isBatteryEnabled = true,
            isBluetoothEnabled = true,
            isWifiEnabled = true,
            isGalleryExifDataEnabled = true,
            isMediaMetadataEnabled = true,
            isProfileEnabled = true,
            isInstalledAppsEnabled = true,
            isLocationEnabled = true
        )

        workViewModel.collectDataSdkItem(beginiDataSdkOptions)
    }
    private fun setupAuthObservers() {
        val request = GetNewTokenRequest(
            uid = "test jithin",
            integrationId = "61ebc61a40d78bbca587d6c0",
        )

        viewModel.getNewSessionToken(
            apiKey = "a6d1c57a-7a57-40a3-a36e-9c5ccc7a9802",
            request = request
        ).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data ->
                            Log.e("Jithin", " Success Access Token: ${data.body()?.accessToken}")
                            var token = data.body()?.accessToken
                            if (token != null)
                                setupFileUploadObservers(token)
                        }
                    }
                    Status.ERROR -> {
                        Log.e("Jithin", " Error in api call")
                    }
                    Status.LOADING -> {
                        Log.e("Jithin", " Loading in api call")
                    }
                }
            }
        })
    }

    private fun setupFileUploadObservers(accessToken: String) {
        val filePath =
            applicationContext.externalCacheDir?.absolutePath +
                    AppConstants.LOCAL_FILES.zip_folder +
                    AppConstants.LOCAL_FILES.zip_final_file

        val request = MultipartBody.Part.createFormData(
            getString(R.string.parameter_name_in_api),
            File(filePath.toUri().toString()).name,
            RequestBody.create(
                "application/*".toMediaTypeOrNull(),
                File(filePath)
            )
        )
    }

}