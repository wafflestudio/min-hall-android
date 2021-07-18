package com.wafflestudio.snucse.minhall

import android.annotation.SuppressLint
import android.graphics.Matrix
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.otaliastudios.zoom.ZoomEngine
import com.wafflestudio.snucse.minhall.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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
}
