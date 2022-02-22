package com.begini.androidsdkv2.core
import java.io.Serializable

  class BeginiDataSdkOptions(
    var isContactsEnabled: Boolean = false,
    var isSmsEnabled: Boolean = false,
    var isInstalledAppsEnabled: Boolean = false,
    var isProfileEnabled: Boolean = false,
    var isBatteryEnabled: Boolean = false,
    var isBluetoothEnabled: Boolean = false,
    var isWifiEnabled: Boolean = false,
    var isMediaMetadataEnabled: Boolean = false,
    var isGalleryExifDataEnabled: Boolean = false,
    var isCallsEnabled: Boolean = false,
    var isCalendarEnabled: Boolean = false,
    var isLocationEnabled: Boolean = false,
) : Serializable