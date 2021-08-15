package com.wafflestudio.snucse.minhall.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.databinding.FragmentReservationBinding
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.view.AppBar
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class ReservationFragment(private val reservation: Reservation) : BaseFragment() {

    companion object {
        const val TAG = "Reservation"
    }

    private val seatMapViewModel: SeatMapViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentReservationBinding.inflate(inflater, container, false)

        initializeViews(binding)
        observeViewModels(binding)

        return binding.root
    }


    private fun initializeViews(binding: FragmentReservationBinding) {
        initializeAppBar(binding.appBar)
        initializeTexts(binding)
        initializeButtons(binding)
    }

    private fun initializeAppBar(appBar: AppBar) {
        appBar.setOnSettingsPressedListener {
            (activity as? MainActivity)?.toSetting()
        }
    }

    private fun initializeTexts(binding: FragmentReservationBinding) {
        binding.timeRangeText.text = "${reservation.startAt} ~ ${reservation.endAt}"
    }

    private fun initializeButtons(binding: FragmentReservationBinding) {
        binding.ctaButton.setOnClickListener {
            (activity as? MainActivity)?.toElongateReservation()
        }
    }

    private fun observeViewModels(binding: FragmentReservationBinding) {
        seatMapViewModel.observeSeats()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ seats ->
                binding.reservationMap.seats = seats
            }, { t ->
                Timber.e(t)
            })
    }
}
