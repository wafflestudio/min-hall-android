package com.wafflestudio.snucse.minhall.network.seat

import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.model.Time
import io.reactivex.rxjava3.core.Single

class SeatApiService(private val seatRetrofitService: SeatRetrofitService) {

    fun getSeats(startAt: Time, endAt: Time): Single<List<Seat>> =
        seatRetrofitService.getSeats(startAt.toString(), endAt.toString())
            .map { seatsDto ->
                seatsDto.seats.map { seatDto ->
                    val mode = when {
                        seatDto.isAvailable -> Seat.Mode.AVAILABLE
                        seatDto.isReserved -> Seat.Mode.TAKEN
                        else -> Seat.Mode.DISABLED
                    }

                    Seat(seatDto.id, mode)
                }
            }
}
