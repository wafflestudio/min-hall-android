package com.wafflestudio.snucse.minhall.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
class Time(val hour: Int, val minute: Int) : Parcelable {

    companion object {
        val currentTime
            get() = Time(
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
            ).roundToNext30Minutes()
    }

    constructor(hhmm: String) : this(
        hhmm.split(":")[0].toInt(),
        hhmm.split(":")[1].toInt(),
    )

    override fun equals(other: Any?): Boolean =
        other is Time && other.minute == minute && other.hour == hour

    override fun hashCode(): Int = hour.hashCode() * 31 + minute.hashCode()

    override fun toString(): String = String.format("%02d:%02d", hour, minute)

    fun roundToNext30Minutes(): Time {
        return if (minute > 30) {
            Time((hour + 1) % 24, 0)
        } else {
            Time(hour, 30)
        }
    }

    fun incrementHour(): Time = Time((hour + 1) % 24, minute)
    fun decrementHour(): Time = Time((hour + 23) % 24, minute)

    fun incrementMinute(): Time = Time(
        (hour + (minute + 30) / 60) % 24,
        (minute + 30) % 60,
    )

    fun decrementMinute(): Time = Time(
        (hour + 23 + (minute + 30) / 60) % 24,
        (minute + 30) % 60,
    )

    fun checkRoomForMinuteIncrement(upperBound: Time): Boolean {
        val next = this.incrementMinute()
        return next.lessOrEqual(upperBound)
    }

    fun checkRoomForHourIncrement(upperBound: Time): Boolean {
        val next = this.incrementHour()
        return next.lessOrEqual(upperBound)
    }

    fun checkRoomForMinuteDecrement(lowerBound: Time): Boolean {
        val next = this.decrementMinute()
        return next.greaterOrEqual(lowerBound)
    }

    fun checkRoomForHourDecrement(lowerBound: Time): Boolean {
        val next = this.decrementHour()
        return next.greaterOrEqual(lowerBound)
    }

    fun bound(lowerBound: Time, upperBound: Time): Time = when {
        lessOrEqual(lowerBound) -> lowerBound
        greaterOrEqual(upperBound) -> upperBound
        else -> this
    }

    fun greaterOrEqual(than: Time): Boolean = when {
        hour > than.hour -> true
        hour == than.hour -> minute >= than.minute
        else -> false
    }

    private fun lessOrEqual(than: Time): Boolean = when {
        hour < than.hour -> true
        hour == than.hour -> minute <= than.minute
        else -> false
    }
}
