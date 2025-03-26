package com.example.newsapplication.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface AppNewsApi {
    @GET("everything")
    suspend fun getAppleNews(
        @Query("q") query: String = "apple",
        @Query("from") from: String = "2025-03-24",
        @Query("to") to: String = "2025-03-24",
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") apiKey: String = RetrofitClient.API_KEY
    ): ApiResponse.NewsResponse

    @GET("everything")
    suspend fun getTeslaNews(
        @Query("q") query: String = "tesla",
        @Query("from") from: String = "2025-02-25",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = RetrofitClient.API_KEY
    ): ApiResponse.NewsResponse

    @GET("top-headlines")
    suspend fun getUSBusinessHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String = "business",
        @Query("apiKey") apiKey: String = RetrofitClient.API_KEY
    ): ApiResponse.NewsResponse

    @GET("top-headlines")
    suspend fun getTechCrunchHeadlines(
        @Query("sources") sources: String = "techcrunch",
        @Query("apiKey") apiKey: String = RetrofitClient.API_KEY
    ): ApiResponse.NewsResponse

    @GET("everything")
    suspend fun getWSJArticles(
        @Query("domains") domains: String = "wsj.com",
        @Query("apiKey") apiKey: String = RetrofitClient.API_KEY
    ): ApiResponse.NewsResponse
}