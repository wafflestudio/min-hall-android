package com.wafflestudio.snucse.minhall

import android.app.Application
import android.content.Intent
import com.facebook.stetho.Stetho
import com.wafflestudio.snucse.minhall.preference.AppPreference
import com.wafflestudio.snucse.minhall.view.ui.login.LoginActivity
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var preference: AppPreference

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }

    fun signOut() {
        preference.clear()
        val loginIntent = LoginActivity.intent(this).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(loginIntent)
    }
}
