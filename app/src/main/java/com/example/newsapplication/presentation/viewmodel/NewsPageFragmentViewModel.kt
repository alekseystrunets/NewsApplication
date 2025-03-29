package com.example.newsapplication.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.newsapplication.data.db.entity.Bookmark
import com.example.newsapplication.data.db.entity.UserBookmark
import com.example.newsapplication.data.db.repository.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsPageFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val bookmarksDao = AppDatabase.getDatabase(application).bookmarksDao()

    fun saveToBookmarks(bookmark: Bookmark, userEmail: String) {
        CoroutineScope(Dispatchers.IO).launch {
            bookmarksDao.insertBookmark(bookmark)
            bookmarksDao.insertUserBookmark(UserBookmark(userEmail, bookmark.id))
        }
    }

    fun removeFromBookmarks(newsId: Int, userEmail: String) {
        CoroutineScope(Dispatchers.IO).launch {
            bookmarksDao.removeUserBookmark(userEmail, newsId)
            if (!bookmarksDao.isBookmarkUsed(newsId)) {
                bookmarksDao.deleteBookmark(newsId)
            }
        }
    }

    fun isNewsBookmarked(userEmail: String, newsId: Int, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val isBooked = bookmarksDao.isNewsBookmarked(userEmail, newsId)
            callback(isBooked)
        }
    }
}