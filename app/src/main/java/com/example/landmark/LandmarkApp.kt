package com.example.landmark

import android.app.Application
import com.example.landmark.repositiry.NewsRepo
import com.example.landmark.services.ApiService
import com.example.landmark.services.Cloud

class LandmarkApp: Application() {

    lateinit var newRepo: NewsRepo
    lateinit var apiService: ApiService
    override fun onCreate() {
        super.onCreate()
        apiService = Cloud.getRetrofit().create(ApiService::class.java)
        newRepo = NewsRepo(apiService)
    }

}