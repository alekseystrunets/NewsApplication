package com.example.newsapplication.presentation

data class NewsItem(
    val title: String,
    val author: String,
    val date: String,
    val imageUrl: String?,
    val content: String?,
    val source: String,
    val articleUrl: String
)