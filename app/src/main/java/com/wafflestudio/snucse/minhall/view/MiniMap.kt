package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.wafflestudio.snucse.minhall.databinding.MiniMapBinding
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.util.dp

class MiniMap @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding: MiniMapBinding

    companion object {
        private const val WIDTH = 184
        private const val HEIGHT = 80
    }

    init {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = MiniMapBinding.inflate(inflater, this)

        initializeViews()
    }

    private fun initializeViews() {
        Seat.seats().forEach { seat ->
            MiniMapSeat(context).addToMap(
                seat.x.dp * WIDTH / 1435,
                seat.y.dp * HEIGHT / 618,
                seat.rotation,
            )
        }
    }

    private fun MiniMapSeat.addToMap(x: Int, y: Int, rotation: Float) {
        this.rotation = rotation
        binding.map.addView(this)
        ConstraintSet().also { set ->
            set.clone(binding.map)

            set.connect(
                id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                x,
            )
            set.connect(
                id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                y,
            )

            set.applyTo(binding.map)
        }
    }

    fun setSeats(seats: List<Seat>) {
        seats.forEach { seat ->
            this.rotation = rotation
            val miniMapSeat = MiniMapSeat(context)
            addView(miniMapSeat)

            ConstraintSet().also { set ->
                set.clone(this)

                set.connect(
                    miniMapSeat.id,
                    ConstraintSet.LEFT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.LEFT,
                    (seat.x.dp * 184) / 1904,
                )
                set.connect(
                    miniMapSeat.id,
                    ConstraintSet.TOP,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.TOP,
                    (seat.y.dp * 184) / 1904,
                )

                set.applyTo(this)
            }
        }
    }
}
