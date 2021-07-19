package com.wafflestudio.snucse.minhall.view

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.Seat
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

class SeatMapViewModel : ViewModel() {

    private val seatsSubject = BehaviorSubject.createDefault(Seat.seats)

    fun observeSeats(): Observable<List<Seat>> = seatsSubject.hide()

    fun observeSeatSelected(): Observable<Boolean> = seatsSubject
        .map { seats -> seats.any { seat -> seat.isSelected } }

    fun selectSeat(seatId: Int) {
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
