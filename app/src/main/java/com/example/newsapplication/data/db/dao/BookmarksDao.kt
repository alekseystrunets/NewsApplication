package com.example.newsapplication.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapplication.data.db.entity.Bookmark
import com.example.newsapplication.data.db.entity.UserBookmark

@Dao
interface BookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: Bookmark)

    @Query("SELECT * FROM bookmarks WHERE id = :newsId")
    suspend fun getBookmarkById(newsId: Int): Bookmark?

    @Query("DELETE FROM bookmarks WHERE id = :newsId")
    suspend fun deleteBookmark(newsId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserBookmark(userBookmark: UserBookmark)

    @Query("SELECT COUNT(*) FROM user_bookmarks WHERE userEmail = :userEmail AND newsId = :newsId")
    suspend fun isNewsBookmarked(userEmail: String, newsId: Int): Boolean

    @Query("DELETE FROM user_bookmarks WHERE userEmail = :userEmail AND newsId = :newsId")
    suspend fun removeUserBookmark(userEmail: String, newsId: Int)

    @Query(
        """
    SELECT bookmarks.* FROM bookmarks 
    INNER JOIN user_bookmarks ON bookmarks.id = user_bookmarks.newsId 
    WHERE user_bookmarks.userEmail = :userEmail
"""
    )
    suspend fun getUserBookmarks(userEmail: String): List<Bookmark>
}