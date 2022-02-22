package com.begini.androidsdkv2.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class FileUploadResponse(
    @Json(name = "code")
    var code: String? = null,
    @Json(name = "show_msg_to_user")
    var showMsgToUser: Boolean? = null,
)