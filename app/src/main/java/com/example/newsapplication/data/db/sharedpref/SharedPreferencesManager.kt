package com.example.newsapplication.data.db.sharedpref

import android.content.Context

object SharedPreferencesManager {
    private const val PREFS_NAME = "NewsAppPrefs"
    private const val KEY_USER_EMAIL = "user_email"
    private const val KEY_USER_LOGIN = "user_login"

    fun saveUserEmail(context: Context, email: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_USER_EMAIL, email).apply()
    }

    fun getUserEmail(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USER_EMAIL, "No email")
    }

    fun saveUserLogin(context: Context, login: String) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_USER_LOGIN, login).apply()
    }

    fun getUserLogin(context: Context): String? {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_USER_LOGIN, "No login")
    }

    fun clearUserData(context: Context) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_USER_EMAIL)
            .remove(KEY_USER_LOGIN)
            .apply()
    }
}