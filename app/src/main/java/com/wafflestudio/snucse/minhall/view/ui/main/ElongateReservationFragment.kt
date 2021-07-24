package com.wafflestudio.snucse.minhall.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.databinding.FragmentElongateReservationBinding
import com.wafflestudio.snucse.minhall.view.AppBar
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment

class ElongateReservationFragment : BaseFragment() {

    companion object {
        const val TAG = "ElongateReservation"

        fun create() = ElongateReservationFragment()
    }

    private val timeSelectViewModel: TimeSelectViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentElongateReservationBinding.inflate(inflater, container, false)

        initializeViews(binding)
        observeViewModels(binding)

        return binding.root
    }

    private fun initializeViews(binding: FragmentElongateReservationBinding) {
        initializeAppBar(binding.appBar)
        initializeTimeSelects(binding)
    }

    private fun initializeAppBar(appBar: AppBar) {
        appBar.setOnBackPressedListener { requireActivity().onBackPressed() }
    }

    private fun initializeTimeSelects(binding: FragmentElongateReservationBinding) {
        binding.startTimeSelect.setOnTimeChangedListener { timeSelectViewModel.setStartTime(it) }
        binding.endTimeSelect.setOnTimeChangedListener { timeSelectViewModel.setEndTime(it) }
    }

    private fun observeViewModels(binding: FragmentElongateReservationBinding) = Unit
}
