package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class InstalledAppInfoModel(
    @Json(name = "first_install_time")
    val firstInstalledTime: Long,
    @Json(name = "last_update_time")
    val lastUpdatedTime: Long,
    @Json(name = "version_name")
    val versionName: String,
    @Json(name = "version_code")
    val versionCode: Int,
    @Json(name = "package_name")
    val packageName: String
)
