package com.wafflestudio.snucse.minhall.view.ui.main

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.model.Time
import com.wafflestudio.snucse.minhall.network.reservation.ReservationApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val reservationApiService: ReservationApiService,
) : ViewModel() {

    private val reservationSubject =
        BehaviorSubject.createDefault(Reservation("", Time(0, 0), Time(0, 0)))

    fun createReservation(seatId: String, startAt: Time, endAt: Time): Single<Reservation> =
        reservationApiService.createReservation(seatId, startAt, endAt)
            .doOnSuccess { reservationSubject.onNext(it) }
}
