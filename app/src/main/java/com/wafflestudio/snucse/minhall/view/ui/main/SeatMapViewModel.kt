package com.wafflestudio.snucse.minhall.view.ui.main

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.Seat
import com.wafflestudio.snucse.minhall.model.SeatPosition
import com.wafflestudio.snucse.minhall.model.Time
import com.wafflestudio.snucse.minhall.network.seat.SeatApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class SeatMapViewModel @Inject constructor(
    private val seatApiService: SeatApiService,
) : ViewModel() {

    private val seatsSubject =
        BehaviorSubject.createDefault(SeatPosition.seatIdToPosition.keys.map { Seat(it) })

    fun observeSeats(): Observable<List<Seat>> = seatsSubject.hide()

    fun observeSeatSelected(): Observable<Boolean> = seatsSubject
        .map { seats -> seats.any { seat -> seat.isSelected } }

    fun getSeats(startAt: Time, endAt: Time): Completable = seatApiService.getSeats(startAt, endAt)
        .doOnSuccess {
            seatsSubject.onNext(it)
        }
        .ignoreElement()

    fun selectSeat(seatId: String) {
        val newSeatState = seatsSubject.value.map { seat ->
            when {
                seat.id == seatId -> seat.toggleSelect()
                seat.isSelected -> seat.toggleSelect()
                else -> seat
            }
        }
        seatsSubject.onNext(newSeatState)
    }
}
