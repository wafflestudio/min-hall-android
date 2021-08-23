package com.wafflestudio.snucse.minhall.view.ui.reservation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.ActivityReservationBinding
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import com.wafflestudio.snucse.minhall.view.ui.main.ReservationViewModel
import com.wafflestudio.snucse.minhall.view.ui.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ReservationActivity : BaseActivity() {

    companion object {
        private const val RESERVATION_KEY = "ReservationExtraKey"

        fun intent(context: Context, reservation: Reservation) =
            Intent(context, ReservationActivity::class.java).apply {
                putExtra(RESERVATION_KEY, reservation)
            }
    }

    private lateinit var binding: ActivityReservationBinding

    private val reservationViewModel: ReservationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reservation = intent.getParcelableExtra<Reservation>(RESERVATION_KEY)
            ?: throw IllegalStateException("reservation cannot be null")

        reservationViewModel.reservation = Optional.of(reservation)

        initializeViews()
        observeViewModels()
    }

    private fun initializeViews() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container, ReservationFragment(), ReservationFragment.TAG)
        }
    }

    private fun observeViewModels() {

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

    override fun toSetting() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container, SettingFragment(), SettingFragment.TAG)
            addToBackStack(null)
        }
    }
}
