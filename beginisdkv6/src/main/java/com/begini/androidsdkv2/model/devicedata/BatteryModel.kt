package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class BatteryModel(
    @Json(name = "charge_percent")
    val charge_percent: Int,
    @Json(name = "is_charging")
    val is_charging: Boolean,
    @Json(name = "time")
    val time: Long
)
