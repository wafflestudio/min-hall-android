package com.wafflestudio.snucse.minhall.view.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintSet
import com.otaliastudios.zoom.ZoomEngine
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.ActivityMainBinding
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.util.dp
import com.wafflestudio.snucse.minhall.view.SeatButton
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import timber.log.Timber

class MainActivity : BaseActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    private val seatMapViewModel: SeatMapViewModel by viewModels()

    private val baselinedZoom
        get() = binding.zoom.height.toFloat() / binding.map.height / binding.zoom.realZoom

    private var idleCalled = false

    private var seatButtons: List<SeatButton> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.map.post {
            initializeZoom()
            initializeScanner()
            initializeMap()

            observeViewModels()
        }
    }

    private fun initializeZoom() {
        binding.zoom.setMaxZoom(baselinedZoom * 2)
        binding.zoom.setMinZoom(baselinedZoom)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initializeScanner() {
        binding.scanner.layoutParams = binding.scanner.layoutParams.apply {
            height = (binding.miniMapInner.height * baselinedZoom).toInt()
            width = height * binding.zoom.width / binding.zoom.height
        }

        binding.zoom.engine.addListener(object : ZoomEngine.Listener {
            override fun onIdle(engine: ZoomEngine) {
                binding.miniMap.visibility = View.INVISIBLE
                binding.map.visibility = View.VISIBLE
                idleCalled = true
            }

            override fun onUpdate(engine: ZoomEngine, matrix: Matrix) {
                if (idleCalled) binding.miniMap.visibility = View.VISIBLE

                val xPercent = engine.computeHorizontalScrollOffset()
                    .toFloat() / engine.computeHorizontalScrollRange()
                val yPercent = engine.computeVerticalScrollOffset()
                    .toFloat() / engine.computeVerticalScrollRange()

                scaleScanner()
                moveScanner(xPercent, yPercent)
            }
        })
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
        seatButtons = Seat.seats.map { seat ->
            SeatButton(this).apply {
                addToMap(seat.x.dp, seat.y.dp)
                setOnClickListener {
                    seatMapViewModel.selectSeat(seat.id)
                }
            }
        }
    }

    private fun SeatButton.addToMap(x: Int, y: Int) {
        binding.map.addView(this)

        ConstraintSet().also { set ->
            set.clone(binding.map)

            set.connect(
                this.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                x,
            )
            set.connect(
                this.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                y,
            )

            set.applyTo(binding.map)
        }
    }

    private fun observeViewModels() {
        seatMapViewModel.observeSeats()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ seats ->
                seats.zip(seatButtons).forEach { (seat, seatButton) ->
                    seatButton.handleMode(seat.mode)
                }
            }, { t ->
                Timber.e(t)
            })

        seatMapViewModel.observeSeatSelected()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ seatSelected ->
                binding.ctaButton.isEnabled = seatSelected
            }, { t ->
                Timber.e(t)
            })
    }
}
