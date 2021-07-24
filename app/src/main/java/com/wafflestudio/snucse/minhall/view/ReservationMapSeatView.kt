package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.util.dp

class ReservationMapSeatView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attributeSet, defStyleAttr) {

    init {
        id = generateViewId()

        background = ContextCompat.getDrawable(context, R.drawable.mini_map_seat_background)

        isEnabled = false
        isActivated = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sideMeasureSpec = MeasureSpec.makeMeasureSpec(6.dp, MeasureSpec.EXACTLY)
        super.onMeasure(sideMeasureSpec, sideMeasureSpec)
    }

    fun handleMode(mode: Seat.Mode) {
        when (mode) {
            Seat.Mode.AVAILABLE -> {
                isEnabled = true
                isActivated = false
            }
            Seat.Mode.SELECTED -> {
                isEnabled = true
                isActivated = true
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
    }
}
