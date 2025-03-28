package com.example.newsapplication.presentation.ui.fragment

import UserAccount
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.data.db.entity.Bookmark
import com.example.newsapplication.data.db.sharedpref.SharedPreferencesManager
import com.example.newsapplication.databinding.FragmentNewsPageBinding
import com.example.newsapplication.presentation.model.NewsItem
import com.example.newsapplication.presentation.viewmodel.NewsPageFragmentViewModel
import com.google.android.material.snackbar.Snackbar

class NewsPageFragment : Fragment() {
    private var _binding: FragmentNewsPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NewsPageFragmentViewModel
    private var currentNewsItem: NewsItem? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsPageBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(NewsPageFragmentViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentNewsItem = arguments?.getParcelable("news_item")
        currentNewsItem?.let { news ->
            loadNewsData(news)
            setupClickListeners(news)
            checkBookmarkStatus(news.id)
        }

        setupBottomMenu()
    }

    private fun checkBookmarkStatus(newsId: Int) {
        val userEmail = SharedPreferencesManager.getUserEmail(requireContext()) ?: return
        viewModel.isNewsBookmarked(userEmail, newsId) { isBookmarked ->
            if (isBookmarked) {
                binding.btnShare.setImageResource(R.drawable.baseline_bookmark_added_24)
            }
        }
    }

    private fun loadNewsData(news: NewsItem) {
        with(binding) {
            Glide.with(requireContext())
                .load(news.imageUrl)
                .placeholder(R.drawable.baseline_block_24)
                .into(imageNews)

            newsTitle.text = news.title
            newsAuthor.text = news.author
            newsDate.text = news.date
            newsDescription.text = news.description
            additionalText.text = news.content
        }
    }

    private fun setupClickListeners(news: NewsItem) {
        binding.btnLink.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(news.articleUrl)))
        }

        binding.btnShare.setOnClickListener {
            saveToBookmarks(news)
        }
    }

    private fun saveToBookmarks(news: NewsItem) {
        val userEmail = SharedPreferencesManager.getUserEmail(requireContext())
        if (userEmail == null || userEmail == "No email") {
            Snackbar.make(binding.root, "Войдите в аккаунт для сохранения", Snackbar.LENGTH_SHORT).show()
            return
        }

        val bookmark = Bookmark(
            id = news.id,
            title = news.title,
            author = news.author,
            date = news.date,
            imageUrl = news.imageUrl,
            content = news.content,
            description = news.description,
            source = news.source,
            articleUrl = news.articleUrl
        )

        viewModel.saveToBookmarks(bookmark, userEmail)
        Snackbar.make(binding.root, "Сохранено в закладки", Snackbar.LENGTH_SHORT).show()
        binding.btnShare.setImageResource(R.drawable.baseline_bookmark_added_24)
    }

    private fun setupBottomMenu() {
        binding.apply {
            btnHome.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, NewsFragment())
                    .addToBackStack(null)
                    .commit()
            }

            btnBookmarks.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, BooksMarkFragment())
                    .addToBackStack(null)
                    .commit()
            }

            btnProfile.setOnClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, UserAccount())
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}