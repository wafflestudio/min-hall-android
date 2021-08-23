package com.wafflestudio.snucse.minhall.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import timber.log.Timber

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("-_-_- onReceive $intent (${intent.getStringExtra("NotificationTypeKey")})")
        when (NotificationUtil.getNotificationType(intent)) {
            NotificationType.ALMOST_EXPIRED -> NotificationUtil.showReservationExpiringNotification(
                context,
                0,
            )
            NotificationType.EXPIRED -> NotificationUtil.showReservationExpiredNotification(
                context,
                1,
            )
            else -> Timber.d("-_-_- uh oh... type is not here...")
        }
    }
}
