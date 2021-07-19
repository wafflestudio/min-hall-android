package com.wafflestudio.snucse.minhall.view.ui

import android.os.Bundle

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(LoginActivity.intent(this))

        finish()
    }
}
