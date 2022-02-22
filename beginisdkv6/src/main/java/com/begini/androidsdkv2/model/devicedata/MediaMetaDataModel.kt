package com.begini.androidsdkv2.model.devicedata

import android.graphics.drawable.GradientDrawable
import com.squareup.moshi.Json

data class MediaMetaDataModel(
    val orientation: String?,
    val idDRM: String?,
    val height: String?,
    val isFavourite: String?,
    val size: String?,
    val title: String?,
    val bucketDisplayName: String?,
    val mimeType: String?,
    val dateAdded: String?,
    val data: String?,
    val id: String?,
    val dateModified: String?,
    val miniThumbMagic: String?,
    val bucketId: String?,
    val displayname: String?,
)
