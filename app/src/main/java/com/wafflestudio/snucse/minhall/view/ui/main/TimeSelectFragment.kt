package com.wafflestudio.snucse.minhall.view.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.databinding.FragmentTimeSelectBinding
import com.wafflestudio.snucse.minhall.model.ReservationSettings
import com.wafflestudio.snucse.minhall.model.Time
import com.wafflestudio.snucse.minhall.network.error.ErrorUtil
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class TimeSelectFragment : BaseFragment() {

    companion object {
        const val TAG = "TimeSelect"
    }

    private var _binding: FragmentTimeSelectBinding? = null
    private val binding get() = _binding!!

    private val timeSelectViewModel: TimeSelectViewModel by activityViewModels()
    private val reservationViewModel: ReservationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        reservationViewModel.getSettings()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { binding.progress.visibility = View.VISIBLE }
            .doFinally { binding.progress.visibility = View.GONE }
            .subscribe({
                setSettingsLimit(it)
            }, { t ->
                ErrorUtil.showToast(requireContext(), t)
            })
            .disposeOnDestroyView()
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

    private fun setSettingsLimit(settings: ReservationSettings) {
        binding.startTimeSelect.upperBound = settings.closeAt
        binding.startTimeSelect.lowerBound = settings.openAt
        binding.startTimeSelect.boundTime()
        binding.endTimeSelect.upperBound = settings.closeAt
        binding.endTimeSelect.lowerBound = settings.openAt
        binding.endTimeSelect.boundTime()
        observeTimeRange()
    }

    private fun observeTimeRange() {
        timeSelectViewModel.observeTimeRange()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (startAt, endAt) ->
                binding.startTimeSelect.upperBound = endAt
                binding.endTimeSelect.lowerBound = startAt
            }, {
                Timber.e(it)
            })
    }

    private fun initializeAppBar() {
        binding.appBar.setOnSettingsPressedListener {
            (activity as? MainActivity)?.toSetting()
        }
    }
}
