package com.wafflestudio.snucse.minhall.view.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.ActivityMainBinding
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    private val reservationViewModel: ReservationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeViews()
        observeViewModels()
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
            add(R.id.seat_map_fragment_container, SeatMapFragment(), SeatMapFragment.TAG)
            add(R.id.fragment_container, TimeSelectFragment(), TimeSelectFragment.TAG)
        }
    }

    private fun observeViewModels() {
        reservationViewModel.observeReservation()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ optional ->
                if (optional.isPresent) {
                    if (supportFragmentManager.findFragmentByTag(ReservationFragment.TAG) == null) {
                        toReservation()
                    }
                } else {
                    supportFragmentManager.findFragmentByTag(ReservationFragment.TAG)
                        ?.let { fragment ->
                            supportFragmentManager.commit {
                                remove(fragment)
                            }
                        }
                }
            }, { t ->
                Timber.e(t)
            })
            .disposeOnDestroy()
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

    private fun toReservation() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container, ReservationFragment(), ReservationFragment.TAG)
        }
    }

    fun toElongateReservation(reservation: Reservation) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(
                R.id.fragment_container,
                ElongateReservationFragment(reservation),
                ElongateReservationFragment.TAG
            )
            addToBackStack(null)
        }
    }
}
