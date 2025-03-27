package com.example.newsapplication.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapplication.utils.NetworkUtils

class NoInternetFragmentViewModel(application: Application) : AndroidViewModel(application) {

    private val _isInternetAvailable = MutableLiveData<Boolean>()
    val isInternetAvailable: LiveData<Boolean> = _isInternetAvailable

    fun checkInternetConnection() {
        _isInternetAvailable.value = NetworkUtils.isInternetAvailable(getApplication())
    }
}