package com.wafflestudio.snucse.minhall.view.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.ActivityMainBinding
import com.wafflestudio.snucse.minhall.model.ReservationSettings
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import com.wafflestudio.snucse.minhall.view.ui.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {
        private const val RESERVATION_SETTINGS_KEY = "ReservationSettingsExtraKey"

        fun intent(context: Context, reservationSettings: ReservationSettings) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(RESERVATION_SETTINGS_KEY, reservationSettings)
            }
    }

    private lateinit var binding: ActivityMainBinding

    private val reservationViewModel: ReservationViewModel by viewModels()

    private val reservationSettings: ReservationSettings
        get() = intent.getParcelableExtra(RESERVATION_SETTINGS_KEY)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
    }

    override fun onResume() {
        super.onResume()

        reservationViewModel.getMyReservation()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, {})
            .disposeOnPause()
    }

    private fun initializeViews() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(
                R.id.seat_map_fragment_container,
                SeatMapFragment(),
                SeatMapFragment.TAG
            )
            add(
                R.id.fragment_container,
                TimeSelectFragment(reservationSettings),
                TimeSelectFragment.TAG
            )
        }
    }

    override fun toSetting() {
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
}
