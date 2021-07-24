package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.util.dp

class SeatButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : androidx.appcompat.widget.AppCompatButton(context, attributeSet, defStyleAttr) {

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
        val sideMeasureSpec = MeasureSpec.makeMeasureSpec(34.dp, MeasureSpec.EXACTLY)
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
