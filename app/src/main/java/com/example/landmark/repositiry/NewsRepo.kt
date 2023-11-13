package com.example.landmark.repositiry

import com.example.landmark.ConstantsLM
import com.example.landmark.entity.NewsData
import com.example.landmark.services.ApiService
import com.example.landmark.services.Cloud

class NewsRepo(private val apiService: ApiService) {

    suspend fun requestNewsData(page: Int): NetworkResult<NewsData> {
        return try {
            val response = apiService.getNewsData(page)
            val body = response.body()
            if(response.isSuccessful && body != null){
                NetworkResult.Success(body)
            }else{
                NetworkResult.Error(ConstantsLM.MSG_NO_DATA)
            }

        }catch (e:java.lang.Exception){
            NetworkResult.Error(ConstantsLM.MSG_NO_DATA)
        }
    }
}

