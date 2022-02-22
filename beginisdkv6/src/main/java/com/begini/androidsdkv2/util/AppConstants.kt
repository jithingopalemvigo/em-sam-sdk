package com.begini.androidsdkv2.util

object AppConstants {
    object FLAVORS {
        const val DEVELOPMENT = "debug"
        const val PRODUCTION = "release"
    }
    object NETWORK {
        const val GET_API_KEY = "/api_keys"
        const val JWT_TOKEN = "/tokens"
        const val FILE_UPLOAD = "/android_data/"
    }
    object LOCAL_FILES {
        const val zip_folder = "/zipped/"
        const val zip_final_file = "/FinalData.zip"
        const val unconverted_folder = "/unconverted_files/"
    }
}