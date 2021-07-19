package com.wafflestudio.snucse.minhall.view.ui

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.Time
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import java.util.*

class TimeSelectViewModel : ViewModel() {

    private val startTimeSubject = BehaviorSubject.createDefault(
        Time(
            Calendar.getInstance().get(Calendar.HOUR),
            Calendar.getInstance().get(Calendar.MINUTE)
        )
    )
    private val endTimeSubject = BehaviorSubject.createDefault(
        Time(
            Calendar.getInstance().get(Calendar.HOUR),
            Calendar.getInstance().get(Calendar.MINUTE)
        )
    )

    fun incrementStartHour() {
        startTimeSubject.onNext(startTimeSubject.value.incrementHour())
    }

    fun decrementStartHour() {
        startTimeSubject.onNext(startTimeSubject.value.decrementHour())
    }

    fun incrementStartMinute() {
        startTimeSubject.onNext(startTimeSubject.value.incrementMinute())
    }

    fun decrementStartMinute() {
        startTimeSubject.onNext(startTimeSubject.value.decrementMinute())
    }

    fun incrementEndHour() {
        endTimeSubject.onNext(endTimeSubject.value.incrementHour())
    }

    fun decrementEndHour() {
        endTimeSubject.onNext(endTimeSubject.value.decrementHour())
    }

    fun incrementEndMinute() {
        endTimeSubject.onNext(endTimeSubject.value.incrementMinute())
    }

    fun decrementEndMinute() {
        endTimeSubject.onNext(endTimeSubject.value.decrementMinute())
    }

    fun observeStartTime(): Observable<Time> = startTimeSubject.hide()
    fun observeEndTime(): Observable<Time> = endTimeSubject.hide()
}
