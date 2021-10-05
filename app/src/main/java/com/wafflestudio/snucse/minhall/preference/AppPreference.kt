package com.wafflestudio.snucse.minhall.preference

import android.content.SharedPreferences
import androidx.core.content.edit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreference @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) {

    var token by prefString(sharedPreferences, PrefKey.TOKEN)
    var username by prefString(sharedPreferences, PrefKey.USERNAME)
    var password by prefString(sharedPreferences, PrefKey.PASSWORD)

    var noticeVersion by prefLong(sharedPreferences, PrefKey.NOTICE_VERSION)
    var warningVersion by prefLong(sharedPreferences, PrefKey.WARNING_VERSION)

    var wifiName by prefString(sharedPreferences, PrefKey.WIFI_NAME)
    var wifiPassword by prefString(sharedPreferences, PrefKey.WIFI_PASSWORD)

    fun clear(commit: Boolean = true) {
        sharedPreferences.edit(commit = commit) { clear() }
    }
}
