package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
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

    private var timeChangedListener: ((Time) -> Unit)? = null

    private val currentTime
        get() = Time(
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
        ).roundToNext30Minutes()

    var time: Time = currentTime
        set(value) {
            field = value
            timeChangedListener?.invoke(value)
            binding.hourText.text = context.getString(R.string.time_format, value.hour)
            binding.minuteText.text = context.getString(R.string.time_format, value.minute)
        }

    init {
        initializeViews()
    }

    fun setOnTimeChangedListener(timeChangedListener: ((Time) -> Unit)?) {
        this.timeChangedListener = timeChangedListener
    }

    private fun initializeViews() {
        binding.hourUpButton.setOnClickListener { time = time.incrementHour() }
        binding.hourDownButton.setOnClickListener { time = time.decrementHour() }
        binding.minuteUpButton.setOnClickListener { time = time.incrementMinute() }
        binding.minuteDownButton.setOnClickListener { time = time.decrementMinute() }
        time = currentTime
    }
}
