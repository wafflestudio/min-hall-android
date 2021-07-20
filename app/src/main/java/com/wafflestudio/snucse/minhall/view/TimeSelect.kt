package com.wafflestudio.snucse.minhall.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.databinding.TimeSelectBinding
import com.wafflestudio.snucse.minhall.model.Time
import java.util.*

class TimeSelect @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attributeSet, defStyleAttr) {

    private val binding: TimeSelectBinding

    private var timeChangedListener: ((Time) -> Unit)? = null

    private val currentTime
        get() = Time(
            Calendar.getInstance().get(Calendar.HOUR),
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
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding = TimeSelectBinding.inflate(inflater, this)

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
