import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.newsapplication.R
import com.example.newsapplication.data.db.repository.AppDatabase
import com.example.newsapplication.data.db.sharedpref.SharedPreferencesManager
import com.example.newsapplication.databinding.FragmentLoginBinding
import com.example.newsapplication.presentation.ui.fragment.NewsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(LoginFragmentViewModel::class.java)
        viewModel.userDao = AppDatabase.getDatabase(requireContext()).userDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        setupObservers()

        binding.registerButton.setOnClickListener{
            navigateToRegistration()
        }
    }



    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            val login = binding.loginEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            binding.loginInputLayout.error = null
            binding.passwordInputLayout.error = null

            viewModel.validateInput(login, password)
        }
    }

    private fun setupObservers() {
        viewModel.isValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid == true) {
                val login = binding.loginEditText.text.toString().trim()

                viewModel.viewModelScope.launch {
                    val user = withContext(Dispatchers.IO) {
                        viewModel.userDao.getUserByLogin(login)
                    }
                    user?.let {
                        SharedPreferencesManager.saveUserEmail(requireContext(), it.email)
                        SharedPreferencesManager.saveUserLogin(requireContext(), it.login)
                        navigateToNews()
                    }
                }
            }
        }

        viewModel.loginError.observe(viewLifecycleOwner) { error ->
            binding.loginInputLayout.error = error
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding.passwordInputLayout.error = error
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun navigateToNews() {
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, NewsFragment())
            .commit()
    }

    private fun navigateToRegistration() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, RegistrationFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}