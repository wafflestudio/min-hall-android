package com.wafflestudio.snucse.minhall.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.databinding.FragmentReservationBinding
import com.wafflestudio.snucse.minhall.view.AppBar
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class ReservationFragment : BaseFragment() {

    companion object {
        const val TAG = "Reservation"

        fun create() = ReservationFragment()
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

    private fun initializeAppBar(appBar: AppBar) = Unit

    private fun initializeTexts(binding: FragmentReservationBinding) {
        // Dummy data
        binding.timeRangeText.text = "15:30 ~ 17:00"
    }

    private fun initializeButtons(binding: FragmentReservationBinding) = Unit

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
