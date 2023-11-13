package com.example.landmark.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.landmark.databinding.ActivitySplashBinding
import kotlinx.coroutines.*

class Splash : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            startActivity(Intent(this@Splash, HomeListActivity::class.java))
            finish()
        }
    }

}