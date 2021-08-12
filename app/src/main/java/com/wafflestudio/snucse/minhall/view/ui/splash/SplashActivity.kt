package com.wafflestudio.snucse.minhall.view.ui.splash

import android.os.Bundle
import com.wafflestudio.snucse.minhall.preference.AppPreference
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import com.wafflestudio.snucse.minhall.view.ui.login.LoginActivity
import com.wafflestudio.snucse.minhall.view.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var preference: AppPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (preference.token.isBlank()) {
            startActivity(LoginActivity.intent(this))
        } else {
            startActivity(MainActivity.intent(this))
        }

        finish()
    }
}
