package com.wafflestudio.snucse.minhall.model

data class Reservation(
    val id: Long,
    val seatId: String,
    val startAt: Time,
    val endAt: Time,
) {

    fun elongate(endAt: Time): Reservation = this.copy(endAt = endAt)
}
