package com.wafflestudio.snucse.minhall.view.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commit
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.ActivityMainBinding
import com.wafflestudio.snucse.minhall.model.Time
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            setReorderingAllowed(true)
            add(R.id.seat_map_fragment_container, SeatMapFragment(), SeatMapFragment.TAG)
            add(R.id.fragment_container, TimeSelectFragment(), TimeSelectFragment.TAG)
        }
    }

    fun toSetting() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container, SettingFragment(), SettingFragment.TAG)
            addToBackStack(null)
        }
    }

    fun toSeatMap() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            supportFragmentManager.findFragmentByTag(TimeSelectFragment.TAG)?.let { hide(it) }
            addToBackStack(null)
        }
    }

    fun toReservation() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container, ReservationFragment(), ReservationFragment.TAG)
        }
    }

    fun toElongateReservation() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_container,
                ElongateReservationFragment(),
                ElongateReservationFragment.TAG
            )
            addToBackStack(null)
        }
    }
}
