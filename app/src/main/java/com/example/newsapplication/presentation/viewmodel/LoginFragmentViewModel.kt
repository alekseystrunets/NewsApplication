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

    private val _authError = MutableLiveData<String?>()
    val authError: LiveData<String?> get() = _authError

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> get() = _isValid

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> get() = _toastMessage

    private val LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]{3,20}$")
    private val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")

    lateinit var userDao: UserDao

    fun validateInput(login: String, password: String) {
        _loginError.value = null
        _passwordError.value = null
        _authError.value = null
        _toastMessage.value = null

        var hasErrors = false

        when {
            login.isEmpty() -> {
                _loginError.value = "Please enter username"
                hasErrors = true
            }
            !LOGIN_PATTERN.matcher(login).matches() -> {
                _loginError.value = "3-20 chars (letters, numbers, ._-)"
                hasErrors = true
            }
        }

        when {
            password.isEmpty() -> {
                _passwordError.value = "Please enter password"
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

        if (!hasErrors) {
            authenticateUser(login, password)
        } else {
            _isValid.value = false
        }
    }

    private fun authenticateUser(login: String, password: String) {
        viewModelScope.launch {
            try {
                val user = userDao.getUserByLoginAndPassword(login, password)
                if (user != null) {
                    _isValid.postValue(true)
                } else {
                    val userExists = userDao.getUserByLogin(login) != null
                    val errorMessage = if (userExists) "Incorrect password" else "User not found"
                    _authError.postValue(errorMessage)
                    _toastMessage.postValue(errorMessage)
                    _isValid.postValue(false)
                }
            } catch (e: Exception) {
                val errorMessage = "Authentication failed: ${e.message}"
                _authError.postValue(errorMessage)
                _toastMessage.postValue(errorMessage)
                _isValid.postValue(false)
            }
        }
    }
}