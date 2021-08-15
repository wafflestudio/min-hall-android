package com.wafflestudio.snucse.minhall.model

data class Reservation(
    val seatId: String,
    val startAt: Time,
    val endAt: Time,
)
