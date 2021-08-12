package com.wafflestudio.snucse.minhall.view.ui.splash

import android.os.Bundle
import com.wafflestudio.snucse.minhall.view.ui.base.BaseActivity
import com.wafflestudio.snucse.minhall.view.ui.login.LoginActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(LoginActivity.intent(this))

        finish()
    }
}
