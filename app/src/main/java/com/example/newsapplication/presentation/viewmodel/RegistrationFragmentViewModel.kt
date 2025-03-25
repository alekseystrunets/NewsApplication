package com.example.newsapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class RegistrationFragmentViewModel : ViewModel() {

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> get() = _loginError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> get() = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    private val LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]{3,20}$")
    private val EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$")
    private val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}\$")

    fun validateInput(login: String, email: String, password: String) {
        _loginError.value = null
        _emailError.value = null
        _passwordError.value = null

        var hasErrors = false

        when {
            login.isEmpty() -> {
                _loginError.value = "Please enter a username"
                hasErrors = true
            }
            !LOGIN_PATTERN.matcher(login).matches() -> {
                _loginError.value = "3-20 characters (letters, numbers, ._-)"
                hasErrors = true
            }
        }

        when {
            email.isEmpty() -> {
                _emailError.value = "Please enter your email"
                hasErrors = true
            }
            !EMAIL_PATTERN.matcher(email).matches() -> {
                _emailError.value = "Please enter a valid email address"
                hasErrors = true
            }
        }

        when {
            password.isEmpty() -> {
                _passwordError.value = "Please enter a password"
                hasErrors = true
            }
            !PASSWORD_PATTERN.matcher(password).matches() -> {
                _passwordError.value = """
                    Password must contain:
                    • 8+ characters
                    • 1 uppercase letter
                    • 1 lowercase letter
                    • 1 number
                    • No spaces
                """.trimIndent()
                hasErrors = true
            }
        }

        _isValid.value = !hasErrors
    }
}