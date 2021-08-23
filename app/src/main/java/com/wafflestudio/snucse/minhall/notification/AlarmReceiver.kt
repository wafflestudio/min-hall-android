package com.wafflestudio.snucse.minhall.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (NotificationUtil.getNotificationType(intent)) {
            NotificationType.ALMOST_EXPIRED -> NotificationUtil.showReservationExpiringNotification(
                context,
                0,
            )
            NotificationType.EXPIRED -> NotificationUtil.showReservationExpiredNotification(
                context,
                1,
            )
        }
    }
}
