package com.begini.androidsdkv2.network.api

import com.begini.androidsdkv2.model.network.GetNewTokenRequest
import okhttp3.MultipartBody

class ApiHelper(val apiService: ApiService) {
    suspend fun getNewSessionToken(
        apiKey: String, request: GetNewTokenRequest
    ) = apiService.getNewSessionToken(apiKey,request)
    suspend fun uploadFile(
        token: String,
        request: MultipartBody.Part?
    ) = apiService.uploadFile(token,request)
}