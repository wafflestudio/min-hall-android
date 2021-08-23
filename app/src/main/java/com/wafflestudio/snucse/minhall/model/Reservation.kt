package com.wafflestudio.snucse.minhall.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Reservation(
    val id: Long,
    val seatId: String,
    val startAt: Time,
    val endAt: Time,
) : Parcelable {

    fun elongate(endAt: Time): Reservation = this.copy(endAt = endAt)
}
