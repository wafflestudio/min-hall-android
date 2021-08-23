package com.wafflestudio.snucse.minhall.network.error

import android.content.Context
import android.widget.Toast
import timber.log.Timber

object ErrorUtil {

    fun showToast(context: Context, t: Throwable) {
        (t as? ApiServerException)?.body?.errorCode?.let { errorCode ->
            Toast.makeText(context, errorCode.resId, Toast.LENGTH_SHORT).show()
        } ?: run {
            Timber.e(t)
        }
    }
}
