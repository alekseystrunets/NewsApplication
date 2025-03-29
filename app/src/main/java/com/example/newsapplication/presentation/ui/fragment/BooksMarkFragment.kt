package com.example.newsapplication.presentation.ui.fragment

import UserAccount
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.R
import com.example.newsapplication.data.db.entity.Bookmark
import com.example.newsapplication.databinding.FragmentBooksMarkBinding
import com.example.newsapplication.presentation.adapter.BookmarksAdapter
import com.example.newsapplication.presentation.model.NewsItem
import com.example.newsapplication.presentation.viewmodel.BooksmarkFragmentViewModel

class BooksMarkFragment : Fragment() {
    private var _binding: FragmentBooksMarkBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: BooksmarkFragmentViewModel
    private lateinit var adapter: BookmarksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksMarkBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BooksmarkFragmentViewModel::class.java)
        setupAdapter()
        setupObservers()
        viewModel.loadBookmarks()

        binding.btnProfile.setOnClickListener {
            toAccountScreen()
        }
        binding.btnHome.setOnClickListener {
            toNewsScreen()
        }
        binding.arrowBack.setOnClickListener {
            back()
        }
    }

    private fun setupAdapter() {
        adapter = BookmarksAdapter { bookmark ->
            openNewsPage(convertToNewsItem(bookmark))
        }

        binding.rvBookmarks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@BooksMarkFragment.adapter
        }
    }

    private fun convertToNewsItem(bookmark: Bookmark): NewsItem {
        return NewsItem(
            id = bookmark.id,
            title = bookmark.title,
            author = bookmark.author,
            date = bookmark.date,
            imageUrl = bookmark.imageUrl,
            content = bookmark.content,
            description = bookmark.description,
            source = bookmark.source,
            articleUrl = bookmark.articleUrl
        )
    }

    private fun openNewsPage(newsItem: NewsItem) {
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

    private fun setupObservers() {
        viewModel.bookmarks.observe(viewLifecycleOwner) { bookmarks ->
            adapter.submitList(bookmarks)
        }

        viewModel.isEmpty.observe(viewLifecycleOwner) { isEmpty ->
            if (isEmpty) {
                binding.rvBookmarks.visibility = View.GONE
                binding.emptyState.visibility = View.VISIBLE
            } else {
                binding.rvBookmarks.visibility = View.VISIBLE
                binding.emptyState.visibility = View.GONE
            }
        }
    }

    private fun toNewsScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NewsFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun toAccountScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, UserAccount())
            .addToBackStack(null)
            .commit()
    }

    private fun back() {
        parentFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}