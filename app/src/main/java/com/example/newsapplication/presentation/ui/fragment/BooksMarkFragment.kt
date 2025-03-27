package com.example.newsapplication.presentation.ui.fragment

import UserAccount
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentBooksMarkBinding

class BooksMarkFragment : Fragment() {

    private var _binding: FragmentBooksMarkBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksMarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnProfile.setOnClickListener {
            toAccountScreen()
        }

        binding.btnHome?.setOnClickListener {
            toNewsScreen()
        }
    }

    private fun toNewsScreen() {
        val newsFragment = NewsFragment()
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, newsFragment)
            .addToBackStack(null).commit()
    }

    private fun toAccountScreen() {
        val userAccount = UserAccount()
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, userAccount)
            .addToBackStack(null).commit()
    }

}
