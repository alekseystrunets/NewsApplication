package com.example.newsapplication.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "bookmarks")
data class Bookmark(
        @PrimaryKey(autoGenerate = true) val id : Int = 0,

        @ColumnInfo(name = "title")
        val title: String,

        @ColumnInfo(name = "author")
        val author: String,

        @ColumnInfo(name = "date")
        val date: String,

        @ColumnInfo(name = "imageUrl")
        val imageUrl: String?,

        @ColumnInfo(name = "content")
        val content: String?,

        @ColumnInfo(name = "description")
        val description: String,

        @ColumnInfo(name = "source")
        val source: String,

        @ColumnInfo(name = "articleUrl")
        val articleUrl: String
)