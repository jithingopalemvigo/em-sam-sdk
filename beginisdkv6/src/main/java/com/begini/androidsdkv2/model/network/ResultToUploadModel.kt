package com.begini.androidsdkv2.model.network

import com.squareup.moshi.Json

data class ResultToUploadModel<T>(
    @Json(name = "type")
    val type: String,
    @Json(name = "integration_id")
    val integrationId: String,
    @Json(name = "uid")
    val uid: String,
    @Json(name = "details")
    val details: T
)


