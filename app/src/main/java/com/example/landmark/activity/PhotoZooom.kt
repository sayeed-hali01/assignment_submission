package com.example.landmark.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.landmark.ConstantsLM

import com.example.landmark.databinding.ActivityPhotoZooomBinding

class PhotoZooom : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoZooomBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoZooomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = intent.getStringExtra(ConstantsLM.PHOTO_URL)

        Glide.with(this)
            .load(url)
            .into(binding.photoView)

        binding.tvShowNews.setOnClickListener {
            val newsContent = intent.getStringExtra(ConstantsLM.NEWS_CONTENT)
            binding.tvFullContent.text = newsContent
            binding.tvShowNews.visibility = View.GONE
        }

    }
}