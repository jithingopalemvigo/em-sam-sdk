package com.begini.androidsdkv2.model.devicedata

import com.squareup.moshi.Json

data class PermissionModel(

    @Json(name = "granted_permissions")
    val grantedPermissionList: Array<String>,
    @Json(name = "denied_permissions")
    val deniedPermission: Array<String>,
)
