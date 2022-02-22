package com.begini.androidsdkv2.network.api

import com.begini.androidsdkv2.BuildConfig
import com.begini.androidsdkv2.util.AppConstants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    const val API_URL_DEV = "https://dev-session-service.begini.co/"
    const val API_URL_LIVE = "https://dev-session-service.begini.co/"

    private fun getRetrofit(): Retrofit {
        var baseURL = ""
        /*managing base url according to build variants*/
        if (BuildConfig.BUILD_TYPE == AppConstants.FLAVORS.DEVELOPMENT) {
            baseURL = API_URL_DEV
        } else {
            baseURL = API_URL_LIVE
        }
        /*setting up basic retrofit builder */
        val builder = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
        //adding callAdaperFactory to manage the error occur while api calls
//            .addCallAdapterFactory(ErrorCallAdapterFactory(ErrorMapper()))
        //setting up http client for logging the api calls
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        val client = httpClient.connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS).build()
        /*adding client to builder to see logs.
        * we need to remove the below line and call
         retrofit = builder.build() if we don't need log*/
        val retrofit =
            if (BuildConfig.BUILD_TYPE == AppConstants.FLAVORS.DEVELOPMENT)
                builder.client(client).build()
            else builder.build()
        return retrofit
    }
    const val API_FILE_UPLOAD_URL_DEV = "https://dev-android-data-service.begini.co/"
    const val API_FILE_UPLOAD_URL_LIVE = "https://dev-android-data-service.begini.co/"
    //Just because there are 2 different base URLs here we are using 2 different Retrofit instances
    private fun getRetrofitForFileUpload(): Retrofit {
        var baseURL = ""
        /*managing base url according to build variants*/
        if (BuildConfig.BUILD_TYPE == AppConstants.FLAVORS.DEVELOPMENT) {
            baseURL = API_FILE_UPLOAD_URL_DEV
        } else {
            baseURL = API_FILE_UPLOAD_URL_LIVE
        }
        /*setting up basic retrofit builder */
        val builder = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                )
            )
        //adding callAdaperFactory to manage the error occur while api calls
//            .addCallAdapterFactory(ErrorCallAdapterFactory(ErrorMapper()))
        //setting up http client for logging the api calls
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        val client = httpClient.connectTimeout(150, TimeUnit.SECONDS)
            .readTimeout(150, TimeUnit.SECONDS)
            .writeTimeout(150, TimeUnit.SECONDS).build()
        /*adding client to builder to see logs.
        * we need to remove the below line and call
         retrofit = builder.build() if we don't need log*/
        val retrofit =
            if (BuildConfig.BUILD_TYPE == AppConstants.FLAVORS.DEVELOPMENT)
                builder.client(client).build()
            else builder.build()
        return retrofit
    }

    val apiService: ApiService = getRetrofit().create(ApiService::class.java)
    val apiServiceFileUpload: ApiService = getRetrofitForFileUpload().create(ApiService::class.java)
}