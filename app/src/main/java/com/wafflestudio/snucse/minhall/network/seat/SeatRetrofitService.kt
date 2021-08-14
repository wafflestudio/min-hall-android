package com.wafflestudio.snucse.minhall.network.seat

import com.wafflestudio.snucse.minhall.network.seat.dto.SeatsDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface SeatRetrofitService {

    @GET("/seats/")
    fun getSeats(
        @Query("startAt") startAt: String,
        @Query("endAt") endAt: String,
    ): Single<SeatsDto>
}
