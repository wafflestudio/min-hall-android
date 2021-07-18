package com.wafflestudio.snucse.minhall

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wafflestudio.snucse.minhall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeZoom()
    }

    private fun initializeZoom() {
        binding.map.post {
            val minZoom = binding.zoom.height.toFloat() / binding.map.height / binding.zoom.realZoom
            binding.zoom.setMaxZoom(minZoom * 2)
            binding.zoom.setMinZoom(minZoom)
        }
    }
}
