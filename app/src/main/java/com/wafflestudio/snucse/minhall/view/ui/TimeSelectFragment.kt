package com.wafflestudio.snucse.minhall.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.FragmentTimeSelectBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber
import java.util.*

class TimeSelectFragment : BaseFragment() {

    companion object {
        const val TAG = "TimeSelect"

        fun create() = TimeSelectFragment()
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
        observeViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeViews() {
        binding.startHourUpButton.setOnClickListener { timeSelectViewModel.incrementStartHour() }
        binding.startHourDownButton.setOnClickListener { timeSelectViewModel.decrementStartHour() }
        binding.startMinuteUpButton.setOnClickListener { timeSelectViewModel.incrementStartMinute() }
        binding.startMinuteDownButton.setOnClickListener { timeSelectViewModel.decrementStartMinute() }
        binding.endHourUpButton.setOnClickListener { timeSelectViewModel.incrementEndHour() }
        binding.endHourDownButton.setOnClickListener { timeSelectViewModel.decrementEndHour() }
        binding.endMinuteUpButton.setOnClickListener { timeSelectViewModel.incrementEndMinute() }
        binding.endMinuteDownButton.setOnClickListener { timeSelectViewModel.decrementEndMinute() }
    }

    private fun observeViewModel() {
        timeSelectViewModel.observeStartTime()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                binding.startHourText.text = getString(R.string.time_format, it.hour)
                binding.startMinuteText.text = getString(R.string.time_format, it.minute)
            }, { t ->
                Timber.e(t)
            })
            .disposeOnDestroyView()

        timeSelectViewModel.observeEndTime()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                binding.endHourText.text = getString(R.string.time_format, it.hour)
                binding.endMinuteText.text = getString(R.string.time_format, it.minute)
            }, { t ->
                Timber.e(t)
            })
            .disposeOnDestroyView()
    }
}
