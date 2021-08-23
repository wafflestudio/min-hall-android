package com.wafflestudio.snucse.minhall.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReservationSettings(
    val openAt: Time,
    val closeAt: Time,
) : Parcelable
