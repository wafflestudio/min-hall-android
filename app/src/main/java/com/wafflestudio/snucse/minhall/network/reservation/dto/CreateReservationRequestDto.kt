package com.wafflestudio.snucse.minhall.network.reservation.dto

data class CreateReservationRequestDto(
    val seatId: String,
    val startAt: String,
    val endAt: String,
)
