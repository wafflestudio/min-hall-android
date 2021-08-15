package com.wafflestudio.snucse.minhall.network.reservation

import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.model.Time
import com.wafflestudio.snucse.minhall.network.reservation.dto.CreateReservationRequestDto
import io.reactivex.rxjava3.core.Single

class ReservationApiService(private val reservationApiService: ReservationRetrofitService) {

    fun createReservation(seatId: String, startAt: Time, endAt: Time): Single<Reservation> =
        reservationApiService.createReservation(
            CreateReservationRequestDto(
                seatId,
                startAt.toString(),
                endAt.toString(),
            )
        )
            .toSingleDefault(Reservation(seatId, startAt, endAt))
}
