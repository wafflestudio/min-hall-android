package com.wafflestudio.snucse.minhall.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
    }

    private fun initializeViews() {
        supportFragmentManager.commit {
//            add(R.id.fragment_container, SeatMapFragment.create(), SeatMapFragment.TAG)
            add(R.id.fragment_container, TimeSelectFragment.create(), TimeSelectFragment.TAG)
        }
    }
}
