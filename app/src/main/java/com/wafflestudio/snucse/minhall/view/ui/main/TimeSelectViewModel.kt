package com.wafflestudio.snucse.minhall.view.ui.main

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.Time
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

    fun setStartTime(time: Time) = startTimeSubject.onNext(time)
    fun setEndTime(time: Time) = endTimeSubject.onNext(time)
}
