package com.begini.androidsdkv2.network.api

import com.begini.androidsdkv2.model.network.FileUploadResponse
import com.begini.androidsdkv2.model.network.GetNewTokenRequest
import com.begini.androidsdkv2.model.network.GetNewTokenResponse
import com.begini.androidsdkv2.model.network.SessionTokenListItem
import com.begini.androidsdkv2.util.AppConstants
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    /*getSampleAccounts won't need for our application this is only need for
    * our sdk, this is for testing since we don't have api key
    * */
    @GET(AppConstants.NETWORK.GET_API_KEY)
    suspend fun getSampleAccounts(): Response<List<SessionTokenListItem>>

    @POST(AppConstants.NETWORK.JWT_TOKEN)
    suspend fun getNewSessionToken(
        @Header("api-key") apiKey: String,
        @Body request: GetNewTokenRequest
    ): Response<GetNewTokenResponse>



    @Multipart
    @POST(AppConstants.NETWORK.FILE_UPLOAD)
    suspend fun uploadFile(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part?=null
    ): Response<FileUploadResponse>

}