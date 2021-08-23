package com.wafflestudio.snucse.minhall.view.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.wafflestudio.snucse.minhall.model.Reservation
import com.wafflestudio.snucse.minhall.network.error.ApiServerException
import com.wafflestudio.snucse.minhall.network.error.ErrorCode
import com.wafflestudio.snucse.minhall.network.error.ErrorUtil
import com.wafflestudio.snucse.minhall.preference.AppPreference
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import com.wafflestudio.snucse.minhall.view.ui.login.LoginActivity
import com.wafflestudio.snucse.minhall.view.ui.main.MainActivity
import com.wafflestudio.snucse.minhall.view.ui.main.ReservationViewModel
import com.wafflestudio.snucse.minhall.view.ui.reservation.ReservationActivity
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    companion object {
        fun intent(context: Context) = Intent(context, SplashActivity::class.java)
    }

    @Inject
    lateinit var preference: AppPreference

    private val reservationViewModel: ReservationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preference.token.isBlank()) {
            startLoginActivity()
        } else {
            checkAndHandleReservation()
        }
    }

    private fun startLoginActivity() {
        startActivity(LoginActivity.intent(this))
        finish()
    }

    private fun checkAndHandleReservation() {
        reservationViewModel.getMyReservation()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ reservation ->
                startReservationActivity(reservation)
            }, { t ->
                if ((t as? ApiServerException)?.body?.errorCode == ErrorCode.TODAY_RESERVATION_NOT_FOUND) {
                    startMainActivity()
                } else {
                    ErrorUtil.showToast(this, t)
                }
            })
            .disposeOnDestroy()
    }

    private fun startMainActivity() {
        reservationViewModel.getSettings()
            .subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ reservationSettings ->
                startActivity(MainActivity.intent(this, reservationSettings))
                finish()
            }, { t ->
                ErrorUtil.showToast(this, t)
            })
    }

    private fun startReservationActivity(reservation: Reservation) {
        startActivity(ReservationActivity.intent(this, reservation))
        finish()
    }
}
