package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.model.SeatPosition
import com.wafflestudio.snucse.minhall.util.dp

class SeatMapView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    companion object {
        const val WIDTH = 1435
        const val HEIGHT = 618
    }

    var seats: List<Seat> = emptyList()
        set(value) {
            if (field != value) {
                initializeViews()
            }
            field = value
            updateViews()
        }
    private var onSeatClickListener: ((String) -> Unit)? = null
    private var seatButtons: Map<String, SeatButton> = emptyMap()

    init {
        background = ContextCompat.getDrawable(context, R.drawable.map)
    }

    fun setOnSeatClickListener(onSeatClickListener: ((String) -> Unit)?) {
        this.onSeatClickListener = onSeatClickListener
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(WIDTH.dp, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(HEIGHT.dp, MeasureSpec.EXACTLY),
        )
    }

    private fun initializeViews() {
        removeAllViews()
        seatButtons = SeatPosition.seatIdToPosition.entries.map { (seatId, position) ->
            seatId to SeatButton(context).apply {
                addToMap(position.x.dp, position.y.dp, position.rotation)
                setOnClickListener {
                    onSeatClickListener?.invoke(seatId)
                }
            }
        }
            .toMap()
    }

    private fun updateViews() {
        seats.forEach { seat ->
            seatButtons[seat.id]?.handleMode(seat.mode)
        }
    }

    private fun SeatButton.addToMap(x: Int, y: Int, rotation: Float) {
        this.rotation = rotation
        this@SeatMapView.addView(this)
        ConstraintSet().also { set ->
            set.clone(this@SeatMapView)

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

            set.applyTo(this@SeatMapView)
        }
    }
}
