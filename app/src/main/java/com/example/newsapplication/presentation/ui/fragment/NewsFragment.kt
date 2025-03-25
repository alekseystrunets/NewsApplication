package com.example.newsapplication.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.databinding.FragmentNewsBinding
import com.example.newsapplication.presentation.NewsItem
import com.example.newsapplication.presentation.adapter.NewsAdapter
import com.google.android.material.tabs.TabLayout

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupTabLayout()
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupTabLayout() {
        val categories = listOf(
            "Apple",
            "Tesla",
            "US business",
            "TechCrunch",
            "Wall Street Journal"
        )

        categories.forEach { category ->
            binding.newsTabLayout.addTab(
                binding.newsTabLayout.newTab().setText(category)
            )
        }

        binding.newsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { position ->
                    updateNewsForCategory(categories[position])
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Загружаем тестовые данные для первой категории
        updateNewsForCategory(categories.first())
    }

    private fun updateNewsForCategory(category: String) {
        // Тестовые данные для демонстрации
        val testNews = when (category) {
            "Apple" -> listOf(
                NewsItem(
                    title = "Apple’s AirPods Max with USB-C will soon support lossless audio",
                    author = "Chris Welch",
                    date = "2025-03-24",
                    imageUrl = null
                ),
                NewsItem(
                    title = "New MacBook Pro with M3 chip announced",
                    author = "Mark Gurman",
                    date = "2025-03-23",
                    imageUrl = null
                )
            )
            "Tesla" -> listOf(
                NewsItem(
                    title = "Tesla unveils new Model 3 with 400-mile range",
                    author = "Fred Lambert",
                    date = "2025-03-22",
                    imageUrl = null
                )
            )
            else -> listOf(
                NewsItem(
                    title = "Breaking news in $category",
                    author = "John Doe",
                    date = "2025-03-21",
                    imageUrl = null
                ),
                NewsItem(
                    title = "Latest updates from $category",
                    author = "Jane Smith",
                    date = "2025-03-20",
                    imageUrl = null
                ),
                NewsItem(
                    title = "Industry trends in $category",
                    author = "Mike Johnson",
                    date = "2025-03-19",
                    imageUrl = null
                )
            )
        }

        newsAdapter.submitList(testNews)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}