package com.wafflestudio.snucse.minhall.network.seat

import com.wafflestudio.snucse.minhall.network.seat.dto.SeatsDto
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface SeatRetrofitService {

    @GET("/seats")
    fun getSeats(): Single<SeatsDto>
}
