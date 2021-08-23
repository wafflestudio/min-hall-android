package com.wafflestudio.snucse.minhall.view.ui.login

import androidx.lifecycle.ViewModel
import com.wafflestudio.snucse.minhall.model.ReservationSettings
import com.wafflestudio.snucse.minhall.network.login.LoginApiService
import com.wafflestudio.snucse.minhall.network.reservation.ReservationApiService
import com.wafflestudio.snucse.minhall.preference.AppPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel @Inject constructor(
    private val loginApiService: LoginApiService,
    private val reservationApiService: ReservationApiService,
    private val appPreference: AppPreference,
) : ViewModel() {

    fun login(username: String, password: String): Single<ReservationSettings> {
        return loginApiService.login(username, password)
            .doOnSuccess {
                appPreference.token = it.value
                appPreference.username = username
                appPreference.password = password
            }
            .flatMap {
                reservationApiService.getReservationSettings()
            }
    }
}
