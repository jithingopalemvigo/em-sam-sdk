package com.begini.androidsdkv2.model.devicedata
import com.squareup.moshi.Json
import org.json.JSONObject

data class ProfileModel(
   /* @Json(name = "settings")
    val settings: PhoneSettings,
    @Json(name = "hardware")
    val hardware: HardwareSettings,*/
    @Json(name = "permission_status")
    val permissionStatus: PermissionModel
)
data class PhoneSettings(
    @Json(name = "settings_information")
    val settingsInfo: JSONObject
)
data class HardwareSettings(
    @Json(name = "screen_information")
    val screenInfo: JSONObject,
    @Json(name = "volume_levels_information")
    val volumeLevelsInfo: JSONObject,
    @Json(name = "device_information")
    val deviceInfo: JSONObject,
)
