package com.example.newsapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.db.dao.UserDao
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class LoginFragmentViewModel : ViewModel() {

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> get() = _loginError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError

    private val _userNotFoundError = MutableLiveData<String?>()
    val userNotFoundError: LiveData<String?> get() = _userNotFoundError

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    private val LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]{3,20}$")
    private val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")

    // Добавляем переменную для UserDao
    var userDao: UserDao? = null

    fun validateInput(login: String, password: String) {
        _loginError.value = null
        _passwordError.value = null
        _userNotFoundError.value = null

        var hasErrors = false

        when {
            login.isEmpty() -> {
                _loginError.value = "Please enter your username"
                hasErrors = true
            }
            !LOGIN_PATTERN.matcher(login).matches() -> {
                _loginError.value = "3-20 characters (letters, numbers, ._-)"
                hasErrors = true
            }
        }

        when {
            password.isEmpty() -> {
                _passwordError.value = "Please enter your password"
                hasErrors = true
            }
            !PASSWORD_PATTERN.matcher(password).matches() -> {
                _passwordError.value = """
                    Password requirements:
                    - 8+ characters
                    - 1 uppercase letter
                    - 1 lowercase letter
                    - 1 number
                    - No spaces
                """.trimIndent()
                hasErrors = true
            }
        }

        if (!hasErrors) {
            checkUserExists(login, password)
        } else {
            _isValid.value = false
        }
    }

    private fun checkUserExists(login: String, password: String) {
        viewModelScope.launch {
            val user = userDao?.getUserByEmailAndPassword(login, password)
            if (user != null) {
                _isValid.value = true
            } else {
                _userNotFoundError.value = "User not found or incorrect credentials"
                _isValid.value = false
            }
        }
    }
}