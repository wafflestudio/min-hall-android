package com.wafflestudio.snucse.minhall.view.ui.main

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import com.otaliastudios.zoom.ZoomEngine
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.FragmentSeatMapBinding
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.network.error.ApiServerException
import com.wafflestudio.snucse.minhall.network.error.ErrorUtil
import com.wafflestudio.snucse.minhall.notification.NotificationUtil
import com.wafflestudio.snucse.minhall.util.showToast
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import com.wafflestudio.snucse.minhall.view.ui.base.BaseFragment
import com.wafflestudio.snucse.minhall.view.ui.reservation.ReservationActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

@AndroidEntryPoint
class SeatMapFragment : BaseFragment() {

    companion object {
        const val TAG = "SeatMap"
    }

    private var _binding: FragmentSeatMapBinding? = null
    private val binding get() = _binding!!

    private val timeSelectViewModel: TimeSelectViewModel by activityViewModels()
    private val seatMapViewModel: SeatMapViewModel by activityViewModels()
    private val reservationViewModel: ReservationViewModel by activityViewModels()

    private val baselinedZoom
        get() = binding.zoom.height.toFloat() / binding.map.height / binding.zoom.realZoom

    private var idleCalled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeatMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.map.post {
            initializeViews()
            observeViewModels()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.zoom.engine.removeListener(scannerZoomEngineListener)
        _binding = null
    }

    private fun initializeViews() {
        initializeAppBar()
        initializeCtaButton()
        initializeZoom()
        initializeScanner()
        initializeMap()
    }

    private fun initializeAppBar() {
        binding.appBar.setOnBackPressedListener { requireActivity().onBackPressed() }
        binding.appBar.setOnSettingsPressedListener {
            (activity as? BaseActivity)?.toSetting()
        }
    }

    private fun initializeCtaButton() {
        binding.ctaButton.setOnClickListener {
            val (startAt, endAt) = timeSelectViewModel.timeRange
            seatMapViewModel.selectedSeat?.id?.let { seatId ->
                reservationViewModel.createReservation(seatId, startAt, endAt)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ reservation ->
                        NotificationUtil.setFutureReservationExpirationNotification(
                            requireContext(),
                            endAt
                        )
                        toReservation(reservation)
                    }, { t ->
                        ErrorUtil.showToast(requireContext(), t)
                    })
            }
        }
    }

    private fun initializeZoom() {
        binding.zoom.setMaxZoom(baselinedZoom * 2)
        binding.zoom.setMinZoom(baselinedZoom)
    }

    private var scannerZoomEngineListener: ZoomEngine.Listener = object : ZoomEngine.Listener {
        override fun onIdle(engine: ZoomEngine) {
            binding.miniMapContainer.visibility = View.INVISIBLE
            binding.map.visibility = View.VISIBLE
            idleCalled = true
        }

        override fun onUpdate(engine: ZoomEngine, matrix: Matrix) {
            if (idleCalled) binding.miniMapContainer.visibility = View.VISIBLE

            scaleScanner()

            val xPercent = engine.computeHorizontalScrollOffset()
                .toFloat() / engine.computeHorizontalScrollRange()
            val yPercent = engine.computeVerticalScrollOffset()
                .toFloat() / engine.computeVerticalScrollRange()

            moveScanner(xPercent, yPercent)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeScanner() {
        binding.scanner.layoutParams = binding.scanner.layoutParams.apply {
            height = (binding.miniMapInner.height * baselinedZoom).toInt()
            width = height * binding.zoom.width / binding.zoom.height
        }

        binding.zoom.engine.addListener(scannerZoomEngineListener)
    }

    private fun scaleScanner() {
        binding.scanner.layoutParams = binding.scanner.layoutParams.apply {
            height = (binding.miniMapInner.height * baselinedZoom).toInt()
            width = height * binding.zoom.width / binding.zoom.height
        }
    }

    private fun moveScanner(xPercent: Float, yPercent: Float) {
        ConstraintSet().also { set ->
            set.clone(binding.miniMapInner)

            set.connect(
                R.id.scanner,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                (xPercent * binding.miniMap.width).toInt()
            )
            set.connect(
                R.id.scanner,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                (yPercent * binding.miniMap.height).toInt()
            )

            set.applyTo(binding.miniMapInner)
        }
    }

    private fun initializeMap() {
        binding.map.setOnSeatClickListener { seatId ->
            seatMapViewModel.selectSeat(seatId)
        }
    }

    private fun observeViewModels() {
        timeSelectViewModel.observeTimeRange()
            .flatMapCompletable { (startAt, endAt) ->
                seatMapViewModel.fetchSeats(startAt, endAt)
                    .subscribeOn(Schedulers.io())
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({}, { t ->
                Timber.e(t)
            })

        seatMapViewModel.observeSeats()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ seats ->
                binding.map.seats = seats
                binding.miniMap.seats = seats
            }, { t ->
                Timber.e(t)
            })
            .disposeOnDestroyView()

        seatMapViewModel.observeSeatSelected()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ seatSelected ->
                binding.ctaButton.isEnabled = seatSelected
            }, { t ->
                Timber.e(t)
            })
            .disposeOnDestroyView()
    }

    private fun toReservation(reservation: Reservation) {
        activity?.run {
            startActivity(ReservationActivity.intent(this, reservation))
            finish()
        }
    }
}
