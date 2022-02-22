package com.begini.androidsdkv2.network.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.begini.androidsdkv2.network.api.ApiHelper
import com.begini.androidsdkv2.network.repository.AuthRepository
import com.begini.androidsdkv2.network.repository.FileUploadRepository
import com.begini.androidsdkv2.ui.home.viewmodel.AuthViewModel
import com.begini.androidsdkv2.ui.home.viewmodel.BeginiDataSdkWorkViewModel
import com.begini.androidsdkv2.ui.home.viewmodel.FileUploadViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(AuthRepository(apiHelper)) as T
        } else if (modelClass.isAssignableFrom(FileUploadViewModel::class.java)) {
            return FileUploadViewModel(FileUploadRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
class BeginiDataSdkViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(BeginiDataSdkWorkViewModel::class.java)) {
            BeginiDataSdkWorkViewModel(application) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}