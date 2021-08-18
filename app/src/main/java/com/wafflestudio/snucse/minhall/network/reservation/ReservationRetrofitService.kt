package com.wafflestudio.snucse.minhall.network.reservation

import com.wafflestudio.snucse.minhall.network.reservation.dto.CreateReservationRequestDto
import com.wafflestudio.snucse.minhall.network.reservation.dto.ReservationDto
import com.wafflestudio.snucse.minhall.network.reservation.dto.UpdateReservationRequestDto
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ReservationRetrofitService {

    @GET("/reservations/me")
    fun getMyReservation(): Single<ReservationDto>

    @POST("/reservations/")
    fun createReservation(@Body body: CreateReservationRequestDto): Single<ReservationDto>

    @DELETE("/reservations/{reservation-id}")
    fun deleteReservation(@Path("reservation-id") reservationId: Long): Completable

    @PATCH("/reservations/{reservation-id}")
    fun updateReservation(
        @Path("reservation-id") reservationId: Long,
        @Body body: UpdateReservationRequestDto,
    ): Completable
}
