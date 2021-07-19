package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.util.dp

class SeatButton(context: Context) : androidx.appcompat.widget.AppCompatButton(context) {

    init {
        id = View.generateViewId()

        background = ContextCompat.getDrawable(
            context,
            R.drawable.seat_button_background
        )

        isEnabled = false
        isActivated = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sideMeasureSpec = MeasureSpec.makeMeasureSpec(42.dp, MeasureSpec.EXACTLY)
        setMeasuredDimension(sideMeasureSpec, sideMeasureSpec)
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
