package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class LocationModel(
    @Json(name = "latitude")
    val latitude: Double,
    @Json(name = "longitude")
    val longitude: Double,
    @Json(name = "altitude")
    val altitude: Double,
    @Json(name = "bearing")
    val bearing: Float,
    @Json(name = "speed")
    val speed: Float,
    @Json(name = "accuracy")
    val accuracy: Float,
    @Json(name = "time")
    val time: Long,
    @Json(name = "provider")
    val provider: String,
    @Json(name = "hasAccuracy")
    val hasAccuracy: Boolean,
    @Json(name = "hasAltitude")
    val hasAltitude: Boolean,
    @Json(name = "hasSpeed")
    val hasSpeed: Boolean,
    @Json(name = "hasBearing")
    val hasBearing: Boolean
)
