package com.begini.androidsdkv2.ui.home.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import com.begini.androidsdk.model.showDialogMessage
import com.begini.androidsdkv2.BeginiApp
import com.begini.androidsdkv2.R
import com.begini.androidsdkv2.core.BeginiDataSdkOptions
import com.begini.androidsdkv2.core.ResultConstants
import com.begini.androidsdkv2.model.devicedata.LocationModel
import com.begini.androidsdkv2.model.network.GetNewTokenRequest
import com.begini.androidsdkv2.network.api.ApiHelper
import com.begini.androidsdkv2.network.api.RetrofitBuilder
import com.begini.androidsdkv2.network.networkutil.Status
import com.begini.androidsdkv2.network.viewmodel.BeginiDataSdkViewModelFactory
import com.begini.androidsdkv2.network.viewmodel.ViewModelFactory
import com.begini.androidsdkv2.ui.base.BaseActivity
import com.begini.androidsdkv2.ui.home.viewmodel.AuthViewModel
import com.begini.androidsdkv2.ui.home.viewmodel.BeginiDataSdkWorkViewModel
import com.begini.androidsdkv2.ui.home.viewmodel.FileUploadViewModel
import com.begini.androidsdkv2.util.AppConstants
import com.begini.androidsdkv2.util.BeginiConstants
import com.begini.androidsdkv2.util.Timber
import com.begini.androidsdkv2.workerutil.FileUtils
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_processing_screen.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception


class BeginiDataSdkFragment() : Fragment() {
    private lateinit var mLocationRequest: LocationRequest

    private lateinit var workViewModel :BeginiDataSdkWorkViewModel
    private lateinit var viewModel: AuthViewModel
    private lateinit var viewModelFileUpload: FileUploadViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_processing_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        if (BeginiApp.instance.dataSdkOptions.isLocationEnabled) {
            enableGps()
            startLocationUpdates()
        }
        collectDataSdkItems()

    }
    private fun setupViewModel() {
        val apiHelper = ApiHelper(RetrofitBuilder.apiService)
        val apiHelperForFileUpload = ApiHelper(RetrofitBuilder.apiServiceFileUpload)
        val factory = ViewModelFactory(apiHelper)
        val factoryForFileUpload = ViewModelFactory(apiHelperForFileUpload)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        viewModelFileUpload = ViewModelProvider(this, factoryForFileUpload).get(FileUploadViewModel::class.java)
        workViewModel = ViewModelProvider(
            this, BeginiDataSdkViewModelFactory(
                requireActivity().application
            )
        ).get(BeginiDataSdkWorkViewModel::class.java)
        workViewModel.outputWorkInfos.observe(viewLifecycleOwner,workInfosObserver())
        collectDataSdkItems()
    }

    private fun setupAuthObservers() {
        val request = GetNewTokenRequest(
            uid = "testemgigo",
            integrationId = "61ebc61a40d78bbca587d6c0",
        )

        viewModel.getNewSessionToken(
            apiKey = "a6d1c57a-7a57-40a3-a36e-9c5ccc7a9802",
            request = request
        ).observe(viewLifecycleOwner, Observer {
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
                        //setting result to Begini instance
                        BeginiApp.instance.dataSdkResult.SERVER_SYNC =
                            ResultConstants.RESULT_FAILED_NETWORK
                        if (!internetIsConnected()) {
                            triggerStartNetworkCalls()
                        }
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
            requireActivity().externalCacheDir?.absolutePath +
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
        viewModelFileUpload.fileUpload(
            token = "Bearer ".plus(accessToken),
            request = request
        ).observe(viewLifecycleOwner, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { data ->
                            Log.e("Jithin", "File uploading success")
                            FileUtils().deleteFinalZipFile(requireActivity())
                            changeDrawableColor(tvSendingData, R.color.indicator_success)
                            //setting result to Begini instance
                            BeginiApp.instance.dataSdkResult.SERVER_SYNC =
                                ResultConstants.RESULT_SUCCESS
                            layoutProcessing.visibility = View.GONE
                            layoutResult.visibility = View.VISIBLE
                            changeDrawableColor(tvSendingData, R.color.indicator_ongoing)
                        }
                    }
                    Status.ERROR -> {
                        Log.e("Jithin", " Error in file upload api call")
                        //setting result to Begini instance
                        BeginiApp.instance.dataSdkResult.SERVER_SYNC =
                            ResultConstants.RESULT_FAILED_NETWORK
                        if (!internetIsConnected()) {
                            triggerStartNetworkCalls()
                        }  }
                    Status.LOADING -> {
                        Log.e("Jithin", "File uploading Loading")
                    }
                }
            }
        })
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

                        triggerStartNetworkCalls()

                    }
                }
            }
            Log.e("Work Status", "count " + completedWorkCount + "\n" + completedWork)
        }
    }

    //simple internet connection checking. so that we can avoid the permission CHANGE_NETWORK_STATE
    private fun internetIsConnected(): Boolean {
        return try {
            val command = "ping -c 1 google.com"
            Runtime.getRuntime().exec(command).waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    private fun triggerStartNetworkCalls() {
        changeDrawableColor(tvCollectingData, R.color.indicator_success)
        changeDrawableColor(tvSendingData, R.color.indicator_ongoing)
        if (internetIsConnected()) {
            setupAuthObservers()
        } else {
            changeDrawableColor(tvSendingData, R.color.indicator_error)
            requireActivity().showDialogMessage(
                title = getString(R.string.titile_dialog_no_interent),
                message = getString(R.string.message_dialog_no_interent),
                positiveButtonName = getString(R.string.btn_try_again),
                positiveButtonClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                    triggerStartNetworkCalls()
                },
                isCancelable = false,
                negativeButtonName = getString(R.string.btn_cancel),

                negativeButtonClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
                    changeDrawableColor(tvSendingData, R.color.indicator_error)
                    dialogInterface.dismiss()
                    activity?.finish()
                })
        }

    }

    fun changeDrawableColor(textView: TextView, color: Int) {
        for (drawable in textView.getCompoundDrawables()) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(
                            tvSendingData.getContext(),
                            color
                        ), PorterDuff.Mode.SRC_IN
                    )
            }
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
            isCalendarEnabled = false,
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

    private fun enableGps() {
        val mLocationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // Checking GPS is enabled
        val isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnabled) {
            startLocationUpdates()
        } else {
            requireActivity().showDialogMessage(
                message = getString(R.string.message_dialog_need_gps),
                title = getString(R.string.title_dialog_need_gps),
                isCancelable = false,
                positiveButtonName = getString(R.string.btn_turn_on_gps),
                negativeButtonName = getString(R.string.btn_cancel),
                positiveButtonClickListener = DialogInterface.OnClickListener { dialogInterface, i ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                    dialogInterface.cancel()
                },
                negativeButtonClickListener = DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.cancel() },
            )
        }
    }

    private fun startLocationUpdates() {

        val UPDATE_INTERVAL = (10 * 1000 /* 10 secs */).toLong()
        val FASTEST_INTERVAL: Long =
            2000 /* 2 sec */ // Create the location request to start receiving updates
        mLocationRequest = LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(UPDATE_INTERVAL)
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL)

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()


        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        settingsClient.checkLocationSettings(locationSettingsRequest)

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        var fusedClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedClient.requestLocationUpdates(
            mLocationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    // do work here
                    onLocationChanged(locationResult.lastLocation)

                }
            },
            Looper.myLooper()
        )
    }

    fun onLocationChanged(location: Location) {
        // New location has now been determined
        val msg = "Updated Location: " +
                java.lang.Double.toString(location.getLatitude()) + "," +
                java.lang.Double.toString(location.getLongitude())
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        Log.e("Location", location.toString())
        if (location != null) {
            BeginiConstants.lastKnownLocation = LocationModel(
                hasAccuracy = location.hasAccuracy(),
                hasAltitude = location.hasAltitude(),
                hasSpeed = location.hasSpeed(),
                hasBearing = location.hasBearing(),
                latitude = location.latitude,
                longitude = location.longitude,
                altitude = location.altitude,
                bearing = location.bearing,
                speed = location.speed,
                accuracy = location.accuracy,
                time = location.time,
                provider = location.provider
            )
        }
    }

}