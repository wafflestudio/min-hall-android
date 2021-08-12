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

    fun clear(commit: Boolean = true) {
        sharedPreferences.edit(commit = commit) { clear() }
    }
}
