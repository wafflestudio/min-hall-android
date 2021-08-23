package com.wafflestudio.snucse.minhall.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.databinding.FragmentElongateReservationBinding
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.network.error.ErrorUtil
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ElongateReservationFragment(private val reservation: Reservation) : BaseFragment() {

    companion object {
        const val TAG = "ElongateReservation"
    }

    private var _binding: FragmentElongateReservationBinding? = null
    private val binding get() = _binding!!

    private val reservationViewModel: ReservationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElongateReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViews() {
        initializeAppBar()
        initializeTimeSelects()
        initializeButtons()
    }

    private fun initializeAppBar() {
        binding.appBar.setOnBackPressedListener { requireActivity().onBackPressed() }
        binding.appBar.setOnSettingsPressedListener {
            (activity as? MainActivity)?.toSetting()
        }
    }

    private fun initializeTimeSelects() {
        binding.startTimeSelect.time = reservation.startAt
        binding.startTimeSelect.upperBound = reservation.startAt
        binding.startTimeSelect.lowerBound = reservation.startAt
        binding.endTimeSelect.time = reservation.endAt
        binding.endTimeSelect.lowerBound = reservation.endAt
        reservationViewModel.getSettings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                binding.endTimeSelect.upperBound = it.closeAt
                binding.endTimeSelect.boundTime()
            }, { t ->
                ErrorUtil.showToast(requireContext(), t)
            })
            .disposeOnDestroyView()
    }

    private fun initializeButtons() {
        binding.ctaButton.setOnClickListener {
            reservationViewModel.elongateReservation(binding.endTimeSelect.time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    requireActivity().onBackPressed()
                }, { t ->
                    ErrorUtil.showToast(requireContext(), t)
                })
                .disposeOnDestroyView()
        }
    }
}
