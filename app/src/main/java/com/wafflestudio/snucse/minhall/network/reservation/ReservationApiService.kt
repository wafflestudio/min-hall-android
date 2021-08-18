package com.wafflestudio.snucse.minhall.network.reservation

import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.model.Time
import com.wafflestudio.snucse.minhall.network.reservation.dto.CreateReservationRequestDto
import com.wafflestudio.snucse.minhall.network.reservation.dto.UpdateReservationRequestDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ReservationApiService(private val reservationRetrofitService: ReservationRetrofitService) {

    fun getMyReservation(): Single<Reservation> =
        reservationRetrofitService.getMyReservation()
            .map { Reservation(it.id, it.seatId, Time(it.startAt), Time(it.endAt)) }

    fun createReservation(seatId: String, startAt: Time, endAt: Time): Single<Reservation> =
        reservationRetrofitService.createReservation(
            CreateReservationRequestDto(
                seatId,
                startAt.toString(),
                endAt.toString(),
            )
        )
            .map { Reservation(it.id, seatId, startAt, endAt) }

    fun deleteReservation(reservationId: Long): Completable =
        reservationRetrofitService.deleteReservation(reservationId)

    fun updateReservation(reservationId: Long, endAt: Time) =
        reservationRetrofitService.updateReservation(
            reservationId,
            UpdateReservationRequestDto(endAt.toString())
        )
}
