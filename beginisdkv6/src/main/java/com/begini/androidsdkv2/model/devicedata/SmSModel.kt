package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class SmSDataModel(
    @Json(name = "sms_body")
    val body:String,
    @Json(name = "sender_phone_number")
    val phoneNumber:String,
    @Json(name = "date")
    val date:String,
    @Json(name = "date_sent")
    val dateSent:String,
    @Json(name = "type")
    val type:String,
    @Json(name = "seen")
    val seen:String,
    @Json(name = "status")
    val status:String,
)
data class SmSModel(
    @Json(name = "inbox")
    val inbox: MutableList<SmSDataModel>,
    @Json(name = "sent")
    val sent: MutableList<SmSDataModel>,
    )