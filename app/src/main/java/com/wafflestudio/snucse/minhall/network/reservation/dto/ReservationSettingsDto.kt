package com.wafflestudio.snucse.minhall.network.reservation.dto

data class ReservationSettingsDto(
    val openTime: String,
    val closeTime: String,
    val wiFiName: String,
    val wiFiPassword: String,
)
