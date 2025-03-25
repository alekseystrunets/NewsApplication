import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.R
import com.example.newsapplication.data.db.repository.AppDatabase
import com.example.newsapplication.databinding.FragmentRegistrationBinding
import com.example.newsapplication.presentation.ui.fragment.NewsFragment

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegistrationFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RegistrationFragmentViewModel::class.java)
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
            firstGreetingForLogin.alpha = 0f
            divider.alpha = 0f
            secondGreetingForLogin.alpha = 0f
            formCard.alpha = 0f
            formCard.translationY = 40f

            firstGreetingForLogin.animate()
                .alpha(1f)
                .setDuration(600)
                .setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    divider.scaleX = 0f
                    divider.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .setDuration(400)
                        .setInterpolator(OvershootInterpolator())
                        .withEndAction {
                            secondGreetingForLogin.animate()
                                .alpha(1f)
                                .setDuration(500)
                                .withEndAction {
                                    formCard.animate()
                                        .alpha(1f)
                                        .translationY(0f)
                                        .setDuration(600)
                                        .setInterpolator(DecelerateInterpolator())
                                        .start()
                                }
                                .start()
                        }
                        .start()
                }
                .start()
        }
    }

    private fun setupClickListeners() {
        binding.registerButton.setOnClickListener {
            val login = binding.loginEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            binding.loginInputLayout.error = null
            binding.emailInputLayout.error = null
            binding.passwordInputLayout.error = null

            viewModel.registerUser(login, email, password)
        }
    }

    private fun setupObservers() {
        viewModel.loginError.observe(viewLifecycleOwner) { error ->
            binding.loginInputLayout.error = error
        }

        viewModel.emailError.observe(viewLifecycleOwner) { error ->
            binding.emailInputLayout.error = error
        }

        viewModel.passwordError.observe(viewLifecycleOwner) { error ->
            binding.passwordInputLayout.error = error
        }

        viewModel.registrationSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                showSuccessMessage()
                navigateToNews()
            }
        }
    }

    private fun showSuccessMessage() {
        Toast.makeText(requireContext(), "Registration successful!", Toast.LENGTH_SHORT).show()
    }

    private fun navigateToNews() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewsFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}