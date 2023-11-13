package com.example.landmark.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.landmark.repositiry.NewsRepo

class HomeViewModelFactory(private val newsRepo: NewsRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(newsRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}