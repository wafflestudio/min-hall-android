package com.wafflestudio.snucse.minhall.view

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.otaliastudios.zoom.ZoomEngine
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.ActivityMainBinding
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.model.Selection
import com.wafflestudio.snucse.minhall.model.tables
import com.wafflestudio.snucse.minhall.util.dp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var selection: Selection? = null
        set(value) {
            field = value
            binding.ctaButton.isEnabled = value != null
        }

    private val baselinedZoom
        get() = binding.zoom.height.toFloat() / binding.map.height / binding.zoom.realZoom

    private var idleCalled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.map.post {
            initializeZoom()
            initializeScanner()
            initializeMap()
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

                binding.scanner.layoutParams = binding.scanner.layoutParams.apply {
                    height = (binding.miniMapInner.height * baselinedZoom).toInt()
                    width = height * binding.zoom.width / binding.zoom.height
                }

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
        })
    }

    private fun initializeMap() {
        tables.forEach { table ->
            table.seats.forEach { seat ->
                val seatButton = Button(this).apply {
                    id = table.index * 12 + seat.index
                    background = ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.seat_button_background
                    )
                    when (seat.mode) {
                        Seat.Mode.AVAILABLE -> {
                            isEnabled = true
                        }
                        Seat.Mode.TAKEN -> {
                            isEnabled = false
                            isActivated = true
                        }
                        Seat.Mode.DISABLED -> {
                            isEnabled = false
                            isActivated = false
                        }
                    }

                    setOnClickListener {
                        selection?.let {
                            it.button.isActivated = false
                            if (it.button == this) {
                                selection = null
                            } else {
                                selection = Selection(this, table, seat)
                                isActivated = true
                            }
                        } ?: run {
                            selection = Selection(this, table, seat)
                            isActivated = true
                        }
                    }
                }
                binding.map.addView(seatButton)
                ConstraintSet().also { set ->
                    set.clone(binding.map)

                    set.connect(
                        seatButton.id,
                        ConstraintSet.LEFT,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.LEFT,
                        seat.x.dp
                    )
                    set.connect(
                        seatButton.id,
                        ConstraintSet.TOP,
                        ConstraintSet.PARENT_ID,
                        ConstraintSet.TOP,
                        seat.y.dp
                    )

                    set.applyTo(binding.map)
                }
                seatButton.layoutParams = seatButton.layoutParams.apply {
                    width = 42.dp
                    height = 42.dp
                }
            }
        }
    }
}
