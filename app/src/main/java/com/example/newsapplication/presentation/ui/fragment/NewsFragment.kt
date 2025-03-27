package com.example.newsapplication.presentation.ui.fragment

import NewsFragmentViewModel
import UserAccount
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentNewsBinding
import com.example.newsapplication.presentation.adapter.NewsAdapter
import com.google.android.material.tabs.TabLayout

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var viewModel: NewsFragmentViewModel

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

        viewModel = ViewModelProvider(this).get(NewsFragmentViewModel::class.java)
        setupRecyclerView()
        setupTabLayout()
        observeViewModel()

        binding.btnBookmarks.setOnClickListener { toBookMarksScreen() }
        binding.btnProfile.setOnClickListener { toAccountScreen() }

        viewModel.loadNews("Apple")
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter().apply {
            onItemClick = { newsItem ->
                val fragment = NewsPageFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable("news_item", newsItem)
                    }
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupTabLayout() {
        listOf("Apple", "Tesla", "US business", "TechCrunch", "Wall Street Journal").forEach {
            binding.newsTabLayout.addTab(binding.newsTabLayout.newTab().setText(it))
        }

        binding.newsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.text?.let { viewModel.loadNews(it.toString()) }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun observeViewModel() {
        viewModel.newsList.observe(viewLifecycleOwner) { news ->
            newsAdapter.submitList(news)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun toBookMarksScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, BooksMarkFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun toAccountScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UserAccount())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}