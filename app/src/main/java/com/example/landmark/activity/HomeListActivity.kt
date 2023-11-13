package com.example.landmark.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.landmark.ConstantsLM
import com.example.landmark.LandmarkApp
import com.example.landmark.adapter.ProductGridAdapter
import com.example.landmark.databinding.ActivityMainBinding
import com.example.landmark.entity.NewsData
import com.example.landmark.repositiry.NetworkResult
import com.example.landmark.viewmodel.HomeViewModel
import com.example.landmark.viewmodel.HomeViewModelFactory

class HomeListActivity : AppCompatActivity() {

    private var isQueued: Boolean = false
    private lateinit var productGridAdapter: ProductGridAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var layoutManager: GridLayoutManager
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = (application as LandmarkApp).newRepo
        val viewModelFactory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        observeLiveData()
        viewModel.requestNewsData(1)
    }

    private fun observeLiveData() {

        viewModel.newsLiveData.observe(this) {
            when(it){
                is NetworkResult.Success -> {
                    populateDatainView(it.data)
                }
                is NetworkResult.Error -> {
                    Toast.makeText(this,ConstantsLM.MSG_NO_DATA,Toast.LENGTH_SHORT).show()
                    binding.progress.visibility = View.INVISIBLE
                }
                else -> {
                }
            }
            binding.progress.visibility = View.INVISIBLE
        }
    }

    private fun populateDatainView(dataNews: NewsData?) {
        if (::productGridAdapter.isInitialized) {
            updateAndNotify(dataNews)
            return
        }
        dataNews?.data?.let {

            productGridAdapter = ProductGridAdapter(dataNews, this)
            layoutManager = GridLayoutManager(this, 2)
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = productGridAdapter
            isLoading = true
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val reachedToLast = totalItemCount - lastVisibleItem == 1 && (dx != 0 || dy != 0)


                if (reachedToLast) {
                    if (!isQueued) {
                        requestNext()
                    }
                }
            }
        })
    }

    private fun requestNext() {
        isQueued = true
        binding.progress.visibility = View.VISIBLE
        val page = productGridAdapter.newsData.meta?.page?.plus(1)
        page?.let { viewModel.requestNewsData(page) }
    }

    private fun updateAndNotify(dataNews: NewsData?) {
        if (dataNews != null) {
            productGridAdapter.addItems(dataNews)
            isQueued = false
        }
    }

}