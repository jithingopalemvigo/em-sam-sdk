package com.begini.androidsdkv2.model.network

import com.squareup.moshi.Json

data class SessionTokenListItem(
    @Json(name = "api_key")
    var apiKey: String? = null,
    @Json(name = "company_id")
    var companyId: String? = null,
    @Json(name = "integration_id")
    var integrationId: String? = null,
)