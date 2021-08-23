package com.wafflestudio.snucse.minhall.view.ui.reservation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wafflestudio.snucse.minhall.databinding.FragmentElongateReservationBinding
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.network.error.ErrorUtil
import com.wafflestudio.snucse.minhall.notification.NotificationUtil
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment
import com.wafflestudio.snucse.minhall.view.ui.main.ReservationViewModel
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
            (activity as? BaseActivity)?.toSetting()
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
        binding.endTimeSelect.setOnTimeChangedListener { endAt ->
            binding.ctaButton.isEnabled = reservation.endAt != endAt
        }
    }

    private fun initializeButtons() {
        binding.ctaButton.setOnClickListener {
            val endAt = binding.endTimeSelect.time
            reservationViewModel.elongateReservation(endAt)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    NotificationUtil.cancelExpirationNotifications(requireContext())
                    NotificationUtil.setFutureReservationExpirationNotification(
                        requireContext(),
                        endAt
                    )
                    requireActivity().onBackPressed()
                }, { t ->
                    ErrorUtil.showToast(requireContext(), t)
                })
                .disposeOnDestroyView()
        }
    }
}
