package com.example.landmark.services

import com.example.landmark.ConstantsLM
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Cloud {
    private var retrofit: Retrofit

    init {
        val client: OkHttpClient = OkHttpClient.Builder().build()
        retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ConstantsLM.BASE_URL)
            .build()
    }

    fun getRetrofit(): Retrofit {
        return retrofit
    }
}