package com.begini.androidsdkv2.model.devicedata

import android.graphics.drawable.GradientDrawable
import com.squareup.moshi.Json

data class GalleryModel(

    @Json(name = "id")
    val id:String,
    @Json(name = "title")
    val title:String,
    @Json(name = "date")
    val date:String,
    @Json(name = "data")
    val data:String,
    @Json(name = "mime_type")
    val mimeType:String,
    @Json(name = "size")
    val size:String,
    @Json(name = "orientation")
    val orientation:String?,
    @Json(name = "width")
    val width:String,
    @Json(name = "height")
    val height:String,
    @Json(name = "display_name")
    val displayName:String
)