import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.data.db.dao.UserDao
import com.example.newsapplication.data.db.entity.User
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RegistrationFragmentViewModel : ViewModel() {

    lateinit var userDao: UserDao

    private val _loginError = MutableLiveData<String?>()
    val loginError: LiveData<String?> get() = _loginError

    private val _emailError = MutableLiveData<String?>()
    val emailError: LiveData<String?> get() = _emailError

    private val _passwordError = MutableLiveData<String?>()
    val passwordError: LiveData<String?> get() = _passwordError

    private val _registrationSuccess = MutableLiveData<Boolean>()
    val registrationSuccess: LiveData<Boolean> get() = _registrationSuccess

    private val LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]{3,20}$")
    private val EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
    private val PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")

    fun registerUser(login: String, email: String, password: String) {
        if (!validateInput(login, email, password)) return

        viewModelScope.launch {
            try {
                if (userDao.getUserByEmail(email) != null) {
                    _emailError.postValue("Email already registered")
                } else {
                    userDao.insertUser(User(email = email, login = login, password = password))
                    _registrationSuccess.postValue(true)
                }
            } catch (e: Exception) {
                _emailError.postValue("Database error")
            }
        }
    }

    private fun validateInput(login: String, email: String, password: String): Boolean {
        var isValid = true

        when {
            login.isEmpty() -> {
                _loginError.value = "Enter username"
                isValid = false
            }
            !LOGIN_PATTERN.matcher(login).matches() -> {
                _loginError.value = "3-20 chars (letters, numbers, ._-)"
                isValid = false
            }
            else -> _loginError.value = null
        }

        when {
            email.isEmpty() -> {
                _emailError.value = "Enter email"
                isValid = false
            }
            !EMAIL_PATTERN.matcher(email).matches() -> {
                _emailError.value = "Invalid email"
                isValid = false
            }
            else -> _emailError.value = null
        }

        when {
            password.isEmpty() -> {
                _passwordError.value = "Enter password"
                isValid = false
            }
            !PASSWORD_PATTERN.matcher(password).matches() -> {
                _passwordError.value = """
                    Password must:
                    • 8+ chars
                    • 1 uppercase
                    • 1 lowercase
                    • 1 number
                    • No spaces
                """.trimIndent()
                isValid = false
            }
            else -> _passwordError.value = null
        }

        return isValid
    }
}