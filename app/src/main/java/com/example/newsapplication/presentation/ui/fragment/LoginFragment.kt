import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
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
        initAnimations()
        setupClickListeners()
        setupObservers()
    }

    private fun initAnimations() {
        binding.apply {
            firstGreetingForLogin.apply {
                alpha = 0f
                translationY = 30f
                animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(600)
                    .setInterpolator(DecelerateInterpolator())
                    .start()
            }

            divider.apply {
                alpha = 0f
                scaleX = 0f
                animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setDuration(400)
                    .setInterpolator(OvershootInterpolator())
                    .setStartDelay(300)
                    .start()
            }

            secondGreetingForLogin.apply {
                alpha = 0f
                translationY = 20f
                animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(500)
                    .setInterpolator(BounceInterpolator())
                    .setStartDelay(600)
                    .start()
            }

            formCard.apply {
                alpha = 0f
                translationY = 40f
                animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setDuration(600)
                    .setStartDelay(900)
                    .start()
            }
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

        binding.registerButton.setOnClickListener {
            navigateToRegistration()
        }
    }

    private fun setupObservers() {
        viewModel.isValid.observe(viewLifecycleOwner) { isValid ->
            if (isValid == true) {
                val login = binding.loginEditText.text.toString().trim()

                viewModel.viewModelScope.launch {
                    val user = viewModel.userDao.getUserByLogin(login)
                    user?.let {
                        SharedPreferencesManager.saveUserEmail(requireContext(), it.email)
                        SharedPreferencesManager.saveUserLogin(requireContext(), it.login)
                    }
                    withContext(Dispatchers.Main) {
                        navigateToNews()
                    }
                }
            }
        }
    }

    private fun navigateToNews() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewsFragment())
            .addToBackStack(null)
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