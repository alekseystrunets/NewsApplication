package com.example.newsapplication.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsItem(
    val title: String,
    val author: String,
    val date: String,
    val imageUrl: String?,
    val content: String?,
    val description: String,
    val source: String,
    val articleUrl: String
) : Parcelable