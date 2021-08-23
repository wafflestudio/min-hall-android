package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.ViewTimeSelectBinding
import com.wafflestudio.snucse.minhall.model.Time
import java.util.*

class TimeSelectView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding = ViewTimeSelectBinding.inflate(LayoutInflater.from(context), this)

    var upperBound: Time = Time(23, 59)
        set(value) {
            field = value
            checkBounds()
        }

    var lowerBound: Time = Time(0, 0)
        set(value) {
            field = value
            checkBounds()
        }

    private var timeChangedListener: ((Time) -> Unit)? = null

    fun boundTime() {
        time = time
    }

    var time: Time = Time.currentTime.bound(lowerBound, upperBound)
        set(value) {
            field = value.bound(lowerBound, upperBound)
            checkBounds()
            timeChangedListener?.invoke(field)
            binding.hourText.text = context.getString(R.string.time_format, field.hour)
            binding.minuteText.text = context.getString(R.string.time_format, field.minute)
        }

    init {
        initializeViews()
        checkBounds()
    }

    fun setOnTimeChangedListener(timeChangedListener: ((Time) -> Unit)?) {
        this.timeChangedListener = timeChangedListener
    }

    private fun initializeViews() {
        binding.hourUpButton.setOnClickListener { time = time.incrementHour() }
        binding.hourDownButton.setOnClickListener { time = time.decrementHour() }
        binding.minuteUpButton.setOnClickListener { time = time.incrementMinute() }
        binding.minuteDownButton.setOnClickListener { time = time.decrementMinute() }
        time = Time.currentTime
    }

    private fun checkBounds() {
        if (time.checkRoomForHourIncrement(upperBound)) {
            binding.hourUpButton.visibility = View.VISIBLE
        } else {
            binding.hourUpButton.visibility = View.INVISIBLE
        }
        if (time.checkRoomForMinuteIncrement(upperBound)) {
            binding.minuteUpButton.visibility = View.VISIBLE
        } else {
            binding.minuteUpButton.visibility = View.INVISIBLE
        }

        if (time.checkRoomForHourDecrement(lowerBound)) {
            binding.hourDownButton.visibility = View.VISIBLE
        } else {
            binding.hourDownButton.visibility = View.INVISIBLE
        }
        if (time.checkRoomForMinuteDecrement(lowerBound)) {
            binding.minuteDownButton.visibility = View.VISIBLE
        } else {
            binding.minuteDownButton.visibility = View.INVISIBLE
        }
    }
}
