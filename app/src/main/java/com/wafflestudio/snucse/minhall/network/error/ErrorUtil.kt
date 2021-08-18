package com.wafflestudio.snucse.minhall.network.error

import android.content.Context
import android.widget.Toast

object ErrorUtil {

    fun showToast(context: Context, t: Throwable) {
        (t as? ApiServerException)?.body?.errorCode?.let { errorCode ->
            Toast.makeText(context, errorCode.resId, Toast.LENGTH_SHORT).show()
        }
    }
}
