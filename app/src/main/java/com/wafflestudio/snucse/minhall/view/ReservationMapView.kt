package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.util.dp

class ReservationMapView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    companion object {
        private const val WIDTH = 269
        private const val HEIGHT = 118
    }

    private var reservationMapSeatViews: List<ReservationMapSeatView> = emptyList()
    var seats: List<Seat> = emptyList()
        set(value) {
            if (field != value) {
                initializeViews()
            }
            field = value
            updateViews()
        }

    init {
        setBackgroundResource(R.drawable.reservation_map)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(WIDTH.dp, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(HEIGHT.dp, MeasureSpec.EXACTLY),
        )
    }

    private fun initializeViews() {
        removeAllViews()
        reservationMapSeatViews = Seat.seats().map { seat ->
            ReservationMapSeatView(context).apply {
                addToMap(
                    seat.x.dp * WIDTH / SeatMapView.WIDTH,
                    seat.y.dp * HEIGHT / SeatMapView.HEIGHT,
                    seat.rotation,
                )
            }
        }
    }

    private fun updateViews() {
        seats.zip(reservationMapSeatViews).forEach { (seat, miniMapSeatView) ->
            miniMapSeatView.handleMode(seat.mode)
        }
    }

    private fun ReservationMapSeatView.addToMap(x: Int, y: Int, rotation: Float) {
        this.rotation = rotation
        this@ReservationMapView.addView(this)
        ConstraintSet().also { set ->
            set.clone(this@ReservationMapView)

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

            set.applyTo(this@ReservationMapView)
        }
    }
}
