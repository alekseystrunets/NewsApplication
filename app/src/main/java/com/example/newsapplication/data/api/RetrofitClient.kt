package com.example.newsapplication.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    const val API_KEY = "2a26da8b46a748da99811d825f2392b4"
    private const val BASE_URL = "https://newsapi.org/v2/"

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val api: AppNewsApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(AppNewsApi::class.java)
    }
}