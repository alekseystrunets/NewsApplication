package com.example.newsapplication.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAnimations()
        setupClickListeners()
    }

    private fun initAnimations() {
        binding.firstGreetingForLogin.apply {
            alpha = 0f
            translationY = 30f
        }
        binding.divider.apply {
            alpha = 0f
            scaleX = 0f
        }
        binding.secondGreetingForLogin.apply {
            alpha = 0f
            translationY = 20f
        }
        binding.formCard.apply {
            alpha = 0f
            translationY = 40f
        }

        binding.firstGreetingForLogin.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(600)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {
                binding.divider.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setDuration(400)
                    .setInterpolator(OvershootInterpolator())
                    .withEndAction {
                        binding.secondGreetingForLogin.animate()
                            .alpha(1f)
                            .translationY(0f)
                            .setDuration(500)
                            .setInterpolator(BounceInterpolator())
                            .withEndAction {
                                binding.formCard.animate()
                                    .alpha(1f)
                                    .translationY(0f)
                                    .setDuration(600)
                                    .start()
                            }
                            .start()
                    }
                    .start()
            }
            .start()
    }

    private fun setupClickListeners() {
        binding.registerButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, RegistrationFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.loginButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, NewsFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}