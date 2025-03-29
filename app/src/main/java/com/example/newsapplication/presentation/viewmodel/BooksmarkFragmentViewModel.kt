package com.example.newsapplication.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapplication.data.db.dao.BookmarksDao
import com.example.newsapplication.data.db.entity.Bookmark
import com.example.newsapplication.data.db.repository.AppDatabase
import com.example.newsapplication.data.db.sharedpref.SharedPreferencesManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksmarkFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val bookmarksDao: BookmarksDao = AppDatabase.getDatabase(application).bookmarksDao()
    private val sharedPrefs = SharedPreferencesManager

    private val _bookmarks = MutableLiveData<List<Bookmark>>()
    val bookmarks: LiveData<List<Bookmark>> = _bookmarks

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    fun loadBookmarks() {
        CoroutineScope(Dispatchers.IO).launch {
            val userEmail = sharedPrefs.getUserEmail(getApplication()) ?: ""
            val bookmarksList = bookmarksDao.getUserBookmarks(userEmail)
            _bookmarks.postValue(bookmarksList)
            _isEmpty.postValue(bookmarksList.isEmpty())
        }
    }
}