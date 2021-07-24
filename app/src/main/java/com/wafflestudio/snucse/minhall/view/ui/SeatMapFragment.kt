package com.wafflestudio.snucse.minhall.view.ui

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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class SeatMapFragment : BaseFragment() {

    companion object {
        const val TAG = "SeatMap"

        fun create() = SeatMapFragment()
    }

    private var _binding: FragmentSeatMapBinding? = null
    private val binding get() = _binding!!

    private val seatMapViewModel: SeatMapViewModel by activityViewModels()

    private val baselinedZoom
        get() = binding.zoom.height.toFloat() / binding.map.height / binding.zoom.realZoom

    private var idleCalled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeatMapBinding.inflate(inflater, container, false)

        binding.map.post {
            initializeZoom()
            initializeScanner()
            initializeMap()

            observeViewModels()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.zoom.engine.removeListener(scannerZoomEngineListener)
        _binding = null
    }

    private fun initializeZoom() {
        binding.zoom.setMaxZoom(baselinedZoom * 2)
        binding.zoom.setMinZoom(baselinedZoom)
    }

    private var scannerZoomEngineListener: ZoomEngine.Listener = object : ZoomEngine.Listener {
        override fun onIdle(engine: ZoomEngine) {
            binding.miniMap.visibility = View.INVISIBLE
            binding.map.visibility = View.VISIBLE
            idleCalled = true
        }

        override fun onUpdate(engine: ZoomEngine, matrix: Matrix) {
            if (idleCalled) binding.miniMap.visibility = View.VISIBLE

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
                (xPercent * binding.miniMapInner.width).toInt()
            )
            set.connect(
                R.id.scanner,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                (yPercent * binding.miniMapInner.height).toInt()
            )

            set.applyTo(binding.miniMapInner)
        }
    }

    private fun initializeMap() {
        binding.map.setOnSeatClickListener {
            seatMapViewModel.selectSeat(it.id)
        }
    }

    private fun observeViewModels() {
        seatMapViewModel.observeSeats()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ seats ->
                binding.map.seats = seats
                binding.miniMapImage.seats = seats
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
}
