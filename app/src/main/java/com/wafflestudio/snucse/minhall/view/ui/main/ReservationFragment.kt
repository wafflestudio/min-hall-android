package com.wafflestudio.snucse.minhall.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.FragmentReservationBinding
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class ReservationFragment : BaseFragment() {

    companion object {
        const val TAG = "Reservation"
    }

    private var _binding: FragmentReservationBinding? = null
    private val binding get() = _binding!!

    private val seatMapViewModel: SeatMapViewModel by activityViewModels()
    private val reservationViewModel: ReservationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeViews()
        observeViewModels()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViews() {
        initializeAppBar()
        initializeButtons()
    }

    private fun initializeAppBar() {
        binding.appBar.setOnSettingsPressedListener {
            (activity as? MainActivity)?.toSetting()
        }
    }

    private fun initializeButtons() {
        binding.ctaButton.setOnClickListener {
            reservationViewModel.reservation.ifPresent { reservation ->
                (activity as? MainActivity)?.toElongateReservation(reservation)
            }
        }

        binding.cancelButton.setOnClickListener {
            showAlertDialog(
                R.string.reservation_cancel_alert,
                {
                    reservationViewModel.cancelReservation()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({}, { Timber.e(it) })
                },
                {}
            )
        }
    }

    private fun observeViewModels() {
        seatMapViewModel.observeSeats()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ seats ->
                binding.reservationMap.seats = seats
            }, { t ->
                Timber.e(t)
            })

        reservationViewModel.observeReservation()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ optional ->
                optional.ifPresent { reservation ->
                    binding.timeRangeText.text = getString(
                        R.string.time_range,
                        reservation.startAt.toString(),
                        reservation.endAt.toString()
                    )
                    if (seatMapViewModel.selectedSeat == null) {
                        seatMapViewModel.selectSeat(reservation.seatId)
                    }
                }
            }, { t ->
                Timber.e(t)
            })
    }
}
