package com.wafflestudio.snucse.minhall.view.ui.main

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.model.ReservationSettings
import com.wafflestudio.snucse.minhall.model.Time
import com.wafflestudio.snucse.minhall.network.reservation.ReservationApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val reservationApiService: ReservationApiService,
) : ViewModel() {

    private val reservationSubject =
        BehaviorSubject.createDefault(Optional.empty<Reservation>())

    val reservation: Optional<Reservation>
        get() = reservationSubject.value

    fun observeReservation(): Observable<Optional<Reservation>> = reservationSubject.hide()

    fun getMyReservation(): Completable =
        reservationApiService.getMyReservation()
            .doOnSuccess { reservationSubject.onNext(Optional.of(it)) }
            .ignoreElement()

    fun createReservation(seatId: String, startAt: Time, endAt: Time): Completable =
        reservationApiService.createReservation(seatId, startAt, endAt)
            .doOnSuccess { reservationSubject.onNext(Optional.of(it)) }
            .ignoreElement()

    fun cancelReservation(): Completable {
        return if (reservationSubject.value.isPresent) {
            val reservationId = reservationSubject.value.get().id
            reservationApiService.deleteReservation(reservationId)
                .doOnComplete { reservationSubject.onNext(Optional.empty()) }
        } else {
            Completable.complete()
        }
    }

    fun elongateReservation(endAt: Time): Completable {
        return if (reservationSubject.value.isPresent) {
            val reservation = reservationSubject.value.get()
            reservationApiService.updateReservation(
                reservation.id,
                endAt,
            )
                .doOnComplete { reservationSubject.onNext(Optional.of(reservation.elongate(endAt))) }
        } else {
            Completable.complete()
        }
    }

    fun getSettings(): Single<ReservationSettings> = reservationApiService.getReservationSettings()
}
