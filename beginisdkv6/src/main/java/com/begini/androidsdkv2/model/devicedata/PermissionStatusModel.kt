package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class PermissionStatusModel(

     val accessCoarseLocation: Boolean,
     val accessFineLocation: Boolean,
     val accessWifiState: Boolean,
     val bluetoothConnect   : Boolean,
     val bluetoothScan: Boolean,
     val readExternalStorage: Boolean,
     val writeExternalStorage: Boolean,
     val readSms : Boolean,
     val readContacts: Boolean,
     val readCallLogs: Boolean,
     val manageExternalStorage: Boolean,
     val bluetooth : Boolean,
)
