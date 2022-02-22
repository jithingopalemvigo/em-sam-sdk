package com.begini.androidsdkv2.network.repository

import com.begini.androidsdkv2.model.network.GetNewTokenRequest
import com.begini.androidsdkv2.network.api.ApiHelper
import okhttp3.MultipartBody

class FileUploadRepository(private val apiHelper: ApiHelper) : BaseRepository() {

    suspend fun uploadFile( token: String,
                            request: MultipartBody.Part?) =
        apiHelper.uploadFile(
            token, request
        )

}