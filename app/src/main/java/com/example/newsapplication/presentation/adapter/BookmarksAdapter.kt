package com.example.newsapplication.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.data.db.entity.Bookmark
import com.example.newsapplication.databinding.RecyclerViewBookmarksItemBinding

class BookmarksAdapter(
    private val onItemClick: (Bookmark) -> Unit
) : ListAdapter<Bookmark, BookmarksAdapter.BookmarkViewHolder>(DiffCallback()) {

    inner class BookmarkViewHolder(private val binding: RecyclerViewBookmarksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bookmark: Bookmark) {
            binding.title.text = bookmark.title
            binding.author.text = bookmark.author
            binding.date.text = bookmark.date

            binding.root.setOnClickListener {
                onItemClick(bookmark)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        val binding = RecyclerViewBookmarksItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookmarkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Bookmark>() {
        override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Bookmark, newItem: Bookmark) =
            oldItem == newItem
    }
}