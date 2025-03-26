package com.example.newsapplication.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentUserAccountBinding

class UserAccount : Fragment() {
    private var _binding: FragmentUserAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnHome.setOnClickListener {
            toNewsScreen()
        }

        binding.btnBookmarks.setOnClickListener {
            toBookmarksScreen()
        }
    }

    private fun toNewsScreen() {
        val newsFragment = NewsFragment()
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, newsFragment)
            .addToBackStack(null).commit()
    }

    private fun toBookmarksScreen() {
        val bookmarksFragment = BooksMarkFragment()
        parentFragmentManager.beginTransaction().replace(R.id.fragment_container, bookmarksFragment)
            .addToBackStack(null).commit()
    }

}
