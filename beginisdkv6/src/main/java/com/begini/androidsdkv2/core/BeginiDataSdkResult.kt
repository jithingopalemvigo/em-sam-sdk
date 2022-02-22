package com.begini.androidsdkv2.core

import java.io.Serializable

class BeginiDataSdkResult(
    var CONTACTS: Int = ResultConstants.RESULT_DEFAULT,
    var SMS: Int = ResultConstants.RESULT_DEFAULT,
    var INSTALLED_APPS: Int = ResultConstants.RESULT_DEFAULT,
    var PROFILE: Int = ResultConstants.RESULT_DEFAULT,
    var BATTERY: Int = ResultConstants.RESULT_DEFAULT,
    var BLUETOOTH: Int = ResultConstants.RESULT_DEFAULT,
    var WIFI: Int = ResultConstants.RESULT_DEFAULT,
    var MEDIA_METADATA: Int = ResultConstants.RESULT_DEFAULT,
    var GALLERY_EXIF: Int = ResultConstants.RESULT_DEFAULT,
    var CALL_LOG: Int = ResultConstants.RESULT_DEFAULT,
    var CALENDER: Int = ResultConstants.RESULT_DEFAULT,
    var LOCATION: Int = ResultConstants.RESULT_DEFAULT,
    var DISK_WRITE: Int = ResultConstants.RESULT_DEFAULT,
    var SERVER_SYNC: Int = ResultConstants.RESULT_DEFAULT,
) : Serializable