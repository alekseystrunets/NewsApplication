package com.example.newsapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.databinding.NewsRecyclerViewItemBinding
import com.example.newsapplication.presentation.NewsItem

class NewsAdapter : ListAdapter<NewsItem, NewsAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRecyclerViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NewsViewHolder(private val binding: NewsRecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: NewsItem) {
            binding.title.text = item.title
            binding.author.text = item.author
            binding.date.text = item.date

            item.imageUrl?.let { url ->
                Glide.with(binding.root)
                    .load(url)
                    .centerCrop()
                    .into(binding.imageViewRecyclerItem)
            } ?: run {
                binding.imageViewRecyclerItem.setImageResource(android.R.color.darker_gray)
            }

            binding.root.setOnClickListener {

            }
        }
    }

    private class NewsDiffCallback : DiffUtil.ItemCallback<NewsItem>() {
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem.articleUrl == newItem.articleUrl
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem == newItem
        }
    }
}