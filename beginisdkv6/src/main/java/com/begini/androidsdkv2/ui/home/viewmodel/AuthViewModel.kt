package com.begini.androidsdkv2.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.begini.androidsdkv2.model.network.GetNewTokenRequest
import com.begini.androidsdkv2.network.networkutil.Resource
import com.begini.androidsdkv2.network.repository.AuthRepository
import com.begini.androidsdkv2.network.repository.FileUploadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun getNewSessionToken(apiKey: String, request: GetNewTokenRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = authRepository.getNewSessionToken(apiKey, request)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}class FileUploadViewModel(private val authRepository: FileUploadRepository) : ViewModel() {

    fun fileUpload(token: String, request: MultipartBody.Part?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = authRepository.uploadFile(token, request)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}