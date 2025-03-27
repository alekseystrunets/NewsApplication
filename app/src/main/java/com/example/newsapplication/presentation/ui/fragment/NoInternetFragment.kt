package com.example.newsapplication.presentation.ui.fragment

import LoginFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentNoInternetBinding
import com.example.newsapplication.presentation.viewmodel.NoInternetFragmentViewModel

class NoInternetFragment : Fragment() {

    private var _binding: FragmentNoInternetBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoInternetFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoInternetBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NoInternetFragmentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        binding.btnRetry.setOnClickListener {
            viewModel.checkInternetConnection()
        }
    }

    private fun setupObservers() {
        viewModel.isInternetAvailable.observe(viewLifecycleOwner) { isAvailable ->
            if (isAvailable) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, LoginFragment())
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}