package com.example.newsapplication.data.db.entity

import androidx.room.Entity


@Entity(tableName = "user_bookmarks", primaryKeys = ["userEmail", "newsId"])
data class UserBookmark(
    val userEmail: String,
    val newsId: Int
)