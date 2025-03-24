package com.example.newsapplication.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.Fragment
import com.example.newsapplication.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAnimations()
    }

    private fun initAnimations() {
        binding.firstGreetingForLogin.alpha = 0f
        binding.divider.alpha = 0f
        binding.secondGreetingForLogin.alpha = 0f
        binding.formCard.alpha = 0f
        binding.formCard.translationY = 40f

        binding.firstGreetingForLogin.animate()
            .alpha(1f)
            .setDuration(600)
            .setInterpolator(DecelerateInterpolator())
            .withEndAction {

                binding.divider.scaleX = 0f
                binding.divider.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .setDuration(400)
                    .setInterpolator(OvershootInterpolator())
                    .withEndAction {

                        binding.secondGreetingForLogin.animate()
                            .alpha(1f)
                            .setDuration(500)
                            .withEndAction {

                                binding.formCard.animate()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}