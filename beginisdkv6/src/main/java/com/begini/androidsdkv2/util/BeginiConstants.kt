package com.begini.androidsdkv2.util

import com.begini.androidsdkv2.model.devicedata.LocationModel


class BeginiConstants {
    companion object {
        const val DATA_SDK_CONTACTS = "DATA_SDK_CONTACTS"
        const val DATA_SDK_SMS = "DATA_SDK_SMS"
        const val DATA_SDK_INSTALLED_APPS = "DATA_SDK_INSTALLED_APPS"
        const val DATA_SDK_PROFILE= "DATA_SDK_PROFILE"
        const val DATA_SDK_BATTERY_INFO = "DATA_SDK_BATTERY_INFO"
        const val DATA_SDK_BLUETOOTH = "DATA_SDK_BLUETOOTH"
        const val DATA_SDK_WIFI = "DATA_SDK_WIFI"
        const val DATA_SDK_MEDIA_METADATA = "DATA_SDK_MEDIA_METADATA"
        const val DATA_SDK_CALL_LOG = "DATA_SDK_CALL_LOG"
        const val DATA_SDK_GALLERY_EXIF_DATA = "DATA_SDK_GALLERY_EXIF_DATA"
        const val DATA_SDK_CREATE_FINAL_ZIP = "DATA_SDK_CREATE_FINAL_ZIP"

            var TAG_OUTPUT = "TAG_OUTPUT"
            var lastKnownLocation: LocationModel? = null
        const val DATA_SDK_COLLECTION_WORK_NAME = "DATA_SDK_COLLECTION_WORK"

        const val FILE_NAME_LOCATION = "location_data.gz"
        const val FILE_NAME_BATTERY = "battery_data.gz"
        const val FILE_NAME_BLUETOOTH = "bluetooth_data.gz"
        const val FILE_NAME_CALENDER = "calender_data.gz"
        const val FILE_NAME_CALL_LOGS = "call_log_data.gz"
        const val FILE_NAME_CONTACTS = "contacts_data.gz"
        const val FILE_NAME_GALLERY_EXIF_DATA = "gallery_exif_data.gz"
        const val FILE_NAME_INSTALLED_APPS = "installed_apps_data.gz"
        const val FILE_NAME_MEDIA_META_DATA = "media_meta_data.gz"
        const val FILE_NAME_PROFILE = "profile_data.gz"
        const val FILE_NAME_SMS = "sms_data.gz"
        const val FILE_NAME_WIFI = "wifi_data.gz"
        var isAllTasksCompleted = false

    }
}