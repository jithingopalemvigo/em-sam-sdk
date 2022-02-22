package com.begini.androidsdkv2.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class GetNewTokenRequest(
    @Json(name = "uid")
    var uid: String? = null,
    @Json(name = "integration_id")
    var integrationId: String? = null,
)