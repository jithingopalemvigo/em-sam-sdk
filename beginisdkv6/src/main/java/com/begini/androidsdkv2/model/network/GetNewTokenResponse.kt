package com.begini.androidsdkv2.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class GetNewTokenResponse(
    @Json(name = "access_token")
    var accessToken: String? = null,
    @Json(name = "url")
    var url: String? = null,
)