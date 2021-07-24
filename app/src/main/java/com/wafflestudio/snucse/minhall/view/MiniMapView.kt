package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.util.dp

class MiniMapView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    companion object {
        private const val WIDTH = 184
        private const val HEIGHT = 80
    }

    private var miniMapSeatViews: List<MiniMapSeatView> = emptyList()
    var seats: List<Seat> = emptyList()
        set(value) {
            if (field != value) {
                initializeViews()
            }
            field = value
            updateViews()
        }

    init {
        setBackgroundResource(R.drawable.mini_map)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(WIDTH.dp, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(HEIGHT.dp, MeasureSpec.EXACTLY),
        )
    }

    private fun initializeViews() {
        miniMapSeatViews = Seat.seats().map { seat ->
            MiniMapSeatView(context).apply {
                addToMap(
                    seat.x.dp * WIDTH / 1435,
                    seat.y.dp * HEIGHT / 618,
                    seat.rotation,
                )
            }
        }
    }

    private fun updateViews() {
        seats.zip(miniMapSeatViews).forEach { (seat, miniMapSeatView) ->
            miniMapSeatView.handleMode(seat.mode)
        }
    }

    private fun MiniMapSeatView.addToMap(x: Int, y: Int, rotation: Float) {
        this.rotation = rotation
        this@MiniMapView.addView(this)
        ConstraintSet().also { set ->
            set.clone(this@MiniMapView)

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

            set.applyTo(this@MiniMapView)
        }
    }
}
