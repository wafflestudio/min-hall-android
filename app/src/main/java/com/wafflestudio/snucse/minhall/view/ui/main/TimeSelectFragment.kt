package com.wafflestudio.snucse.minhall.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.databinding.FragmentTimeSelectBinding
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment

class TimeSelectFragment : BaseFragment() {

    companion object {
        const val TAG = "TimeSelect"
    }

    private var _binding: FragmentTimeSelectBinding? = null
    private val binding get() = _binding!!

    private val timeSelectViewModel: TimeSelectViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeSelectBinding.inflate(inflater, container, false)

        initializeViews()

//        showAlertDialog("Hello", { Timber.d("Confirm") }, null)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViews() {
        initializeAppBar()

        binding.startTimeSelect.setOnTimeChangedListener { timeSelectViewModel.setStartTime(it) }
        binding.endTimeSelect.setOnTimeChangedListener { timeSelectViewModel.setEndTime(it) }

        binding.ctaButton.isEnabled = true
        binding.ctaButton.setOnClickListener {
            (activity as? MainActivity)?.toSeatMap()
        }
    }

    private fun initializeAppBar() {
        binding.appBar.setOnSettingsPressedListener {
            (activity as? MainActivity)?.toSetting()
        }
    }
}
