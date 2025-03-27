package com.example.newsapplication.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.example.newsapplication.data.db.sharedpref.SharedPreferencesManager

class UserAccountViewModel : ViewModel() {
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> get() = _email

    private val _login = MutableLiveData<String>()
    val login: LiveData<String> get() = _login

    fun loadUserData(context: Context) {
        _email.value = SharedPreferencesManager.getUserEmail(context)
        _login.value = SharedPreferencesManager.getUserLogin(context)
    }
}