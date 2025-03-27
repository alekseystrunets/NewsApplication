package com.example.newsapplication.presentation.ui.fragment

import UserAccount
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentNewsPageBinding
import com.example.newsapplication.presentation.model.NewsItem

class NewsPageFragment : Fragment() {
    private var _binding: FragmentNewsPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<NewsItem>("news_item")?.let { news ->
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

                btnLink.setOnClickListener {
                    startActivity(
                        Intent(Intent.ACTION_VIEW, Uri.parse(news.articleUrl))
                    )
                }
            }
        }

        setupBottomMenu()
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
