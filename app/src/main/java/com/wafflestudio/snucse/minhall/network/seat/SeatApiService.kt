package com.wafflestudio.snucse.minhall.network.seat

import com.wafflestudio.snucse.minhall.model.Seat
import io.reactivex.rxjava3.core.Single

class SeatApiService(private val seatRetrofitService: SeatRetrofitService) {

    fun getSeats(): Single<List<Seat>> = seatRetrofitService.getSeats()
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
