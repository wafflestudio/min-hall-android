package com.wafflestudio.snucse.minhall.model

class Time(hour: Int, minute: Int) {

    val hour: Int
    val minute: Int

    init {
        if (minute > 30) {
            this.hour = (hour + 1) % 24
            this.minute = 0
        } else {
            this.hour = hour
            this.minute = 30
        }
    }

    fun incrementHour(): Time = Time((hour + 1) % 24, minute)
    fun decrementHour(): Time = Time((hour + 23) % 24, minute)

    fun incrementMinute(): Time = Time(hour, minute + 30 % 60)
    fun decrementMinute(): Time = Time((hour + 23) % 24, minute + 30 % 60)
}
