package com.wafflestudio.snucse.minhall.network.reservation

import com.wafflestudio.snucse.minhall.model.*
import com.wafflestudio.snucse.minhall.network.reservation.dto.CreateReservationRequestDto
import com.wafflestudio.snucse.minhall.network.reservation.dto.UpdateReservationRequestDto
import com.wafflestudio.snucse.minhall.preference.AppPreference
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class ReservationApiService(
    private val reservationRetrofitService: ReservationRetrofitService,
    private val appPreference: AppPreference,
) {

    fun getMyReservation(): Single<Reservation> =
        reservationRetrofitService.getMyReservation()
            .map { Reservation(it.id, it.seatId, Time(it.startAt), Time(it.endAt)) }

    fun createReservation(seatId: String, startAt: Time, endAt: Time): Single<Reservation> =
        reservationRetrofitService.createReservation(
            CreateReservationRequestDto(
                seatId,
                startAt.toString(),
                endAt.toString(),
            )
        )
            .map { Reservation(it.id, seatId, startAt, endAt) }

    fun deleteReservation(reservationId: Long): Completable =
        reservationRetrofitService.deleteReservation(reservationId)

    fun updateReservation(reservationId: Long, endAt: Time) =
        reservationRetrofitService.updateReservation(
            reservationId,
            UpdateReservationRequestDto(endAt.toString())
        )

    fun getReservationSettings(): Single<ReservationSettings> =
        reservationRetrofitService.getReservationSettings()
            .map {
                val settings = ReservationSettings(
                    openAt = Time(it.openTime),
                    closeAt = Time(it.closeTime),
                )
                appPreference.wifiName = it.wiFiName
                appPreference.wifiPassword = it.wiFiPassword
                settings
            }

    fun getReservationNotice(): Single<NoticePopUp> =
        reservationRetrofitService.getReservationNotice()
            .map {
                val popUp = NoticePopUp(
                    message = it.message,
                    show = it.version > appPreference.noticeVersion,
                    title = it.title,
                )
                appPreference.noticeVersion = it.version
                popUp
            }

    fun getReservationWarning(): Single<WarningPopUp> =
        reservationRetrofitService.getReservationWarning()
            .map {
                val popUp = WarningPopUp(
                    message = it.message,
                    show = it.version > appPreference.warningVersion,
                    title = it.title,
                )
                appPreference.warningVersion = it.version
                popUp
            }
}
