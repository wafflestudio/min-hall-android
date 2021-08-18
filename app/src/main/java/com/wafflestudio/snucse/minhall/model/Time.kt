package com.wafflestudio.snucse.minhall.model

class Time {

    val hour: Int
    val minute: Int

    constructor(hour: Int, minute: Int) {
        this.hour = hour
        this.minute = minute
    }

    constructor(hhmm: String) {
        this.hour = hhmm.split(":")[0].toInt()
        this.minute = hhmm.split(":")[1].toInt()
    }


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
}
