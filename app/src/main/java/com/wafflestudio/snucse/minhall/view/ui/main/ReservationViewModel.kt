package com.wafflestudio.snucse.minhall.view.ui.main

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.*
import com.wafflestudio.snucse.minhall.network.reservation.ReservationApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val reservationApiService: ReservationApiService,
) : ViewModel() {

    private val reservationSubject =
        BehaviorSubject.createDefault(Optional.empty<Reservation>())

    var reservation: Optional<Reservation>
        get() = reservationSubject.value
        set(value) = reservationSubject.onNext(value)

    private val popUpSubject =
        BehaviorSubject.createDefault<PopUp>(EmptyPopUp)

    fun observeReservation(): Observable<Optional<Reservation>> = reservationSubject.hide()

    fun observePopUp(): Observable<PopUp> = popUpSubject.hide()

    fun getMyReservation(): Single<Reservation> =
        reservationApiService.getMyReservation()
            .doOnSuccess { reservationSubject.onNext(Optional.of(it)) }

    fun createReservation(seatId: String, startAt: Time, endAt: Time): Single<Reservation> =
        reservationApiService.createReservation(seatId, startAt, endAt)
            .doOnSuccess { reservationSubject.onNext(Optional.of(it)) }

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

    fun observeReservationDidEnd(): Observable<Unit> =
        Observable.interval(1, TimeUnit.SECONDS)
            .filter { reservation.isPresent }
            .map { Time.currentTime.greaterOrEqual(reservation.get().endAt) }
            .filter { it }
            .map { }

    fun getNoticePopUp(): Completable =
        reservationApiService.getReservationNotice()
            .doOnSuccess { popUpSubject.onNext(it) }
            .ignoreElement()

    fun getWarningPopUp(): Completable =
        reservationApiService.getReservationWarning()
            .doOnSuccess { popUpSubject.onNext(it) }
            .ignoreElement()
}
