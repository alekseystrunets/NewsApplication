package com.example.newsapplication.data.api

import com.example.newsapplication.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface AppNewsApi {
    @GET("everything")
    suspend fun getAppleNews(
        @Query("q") query: String = "apple",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int = 5,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): ApiResponse.NewsResponse

    @GET("everything")
    suspend fun getTeslaNews(
        @Query("q") query: String = "tesla",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int = 5,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): ApiResponse.NewsResponse

    @GET("top-headlines")
    suspend fun getUSBusinessHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("pageSize") pageSize: Int = 5,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): ApiResponse.NewsResponse

    @GET("everything")
    suspend fun getTechCrunchHeadlines(
        @Query("q") query: String = "techcrunch",
        @Query("pageSize") pageSize: Int = 5,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): ApiResponse.NewsResponse

    @GET("everything")
    suspend fun getWSJArticles(
        @Query("domains") domains: String = "wsj.com",
        @Query("pageSize") pageSize: Int = 5,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): ApiResponse.NewsResponse
}