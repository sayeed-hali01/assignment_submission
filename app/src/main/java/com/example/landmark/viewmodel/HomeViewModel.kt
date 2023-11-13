package com.example.landmark.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.landmark.entity.NewsData
import com.example.landmark.repositiry.NetworkResult
import com.example.landmark.repositiry.NewsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeViewModel(private val newsRepo: NewsRepo) : ViewModel() {

    private val _newsLiveData = MutableLiveData<NetworkResult<NewsData>>()
    val newsLiveData: LiveData<NetworkResult<NewsData>> = _newsLiveData

    fun requestNewsData(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            val data = getData(page)
            _newsLiveData.postValue(data)
        }
    }

    private suspend fun getData(page: Int): NetworkResult<NewsData> {
        return newsRepo.requestNewsData(page)
    }
}