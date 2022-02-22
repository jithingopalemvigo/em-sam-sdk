package com.begini.androidsdkv2.model.devicedata
import com.squareup.moshi.Json

data class CallLogModel(
    @Json(name = "number")
    val number:String,
    @Json(name = "type")
    val type:String,
    @Json(name = "date_time_in_milli_sec")
    val dateTimeInMilliSec:String,
    @Json(name = "duration")
    val duration:String,
    @Json(name = "id")
    val id:String
)