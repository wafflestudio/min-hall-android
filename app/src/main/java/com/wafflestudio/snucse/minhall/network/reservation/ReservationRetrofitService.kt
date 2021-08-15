package com.wafflestudio.snucse.minhall.network.reservation

import com.wafflestudio.snucse.minhall.network.reservation.dto.CreateReservationRequestDto
import io.reactivex.rxjava3.core.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface ReservationRetrofitService {

    @POST("/reservations/")
    fun createReservation(@Body body: CreateReservationRequestDto): Completable
}
