package com.example.newsapplication.data.db.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsapplication.data.db.dao.BookmarksDao
import com.example.newsapplication.data.db.dao.UserDao
import com.example.newsapplication.data.db.entity.Bookmark
import com.example.newsapplication.data.db.entity.User
import com.example.newsapplication.data.db.entity.UserBookmark

@Database(
    entities = [User::class, Bookmark::class, UserBookmark::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookmarksDao(): BookmarksDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(AppDatabase::class.java) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "newsApplication_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}