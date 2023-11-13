package com.example.landmark.services

import com.example.landmark.entity.NewsData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("news/page={page_num}")
    suspend fun getNewsData(@Path("page_num") page:Int): Response<NewsData>
}