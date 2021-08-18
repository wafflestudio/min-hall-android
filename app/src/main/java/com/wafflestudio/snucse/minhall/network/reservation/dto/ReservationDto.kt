package com.wafflestudio.snucse.minhall.network.reservation.dto

data class ReservationDto(
    val id: Long,
    val seatId: String,
    val startAt: String,
    val endAt: String,
)
