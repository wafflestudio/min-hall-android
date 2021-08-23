package com.wafflestudio.snucse.minhall.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.wafflestudio.snucse.minhall.R
import com.wafflestudio.snucse.minhall.model.Time
import com.wafflestudio.snucse.minhall.view.ui.main.MainActivity
import com.wafflestudio.snucse.minhall.view.ui.splash.SplashActivity
import org.threeten.bp.*
import org.threeten.bp.temporal.ChronoUnit
import org.threeten.bp.temporal.TemporalAccessor
import org.threeten.bp.temporal.TemporalUnit
import timber.log.Timber
import java.util.*

object NotificationUtil {

    private const val CHANNEL_ID = "MinHall"
    private const val NOTIFICATION_TYPE_KEY = "NotificationTypeKey"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showReservationExpiringNotification(
        context: Context,
        notificationId: Int,
    ) {
        Timber.d("-_-_- showReservationExpiringNotification")
        val pendingIntent = SplashActivity.intent(context).let { splashIntent ->
            PendingIntent.getActivity(
                context,
                0,
                splashIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
            )
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(context.getString(R.string.reservation_expiration_5_minutes_before_title))
            .setContentText(context.getString(R.string.reservation_expiration_5_minutes_before_body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    }

    fun showReservationExpiredNotification(
        context: Context,
        notificationId: Int,
    ) {
        Timber.d("-_-_- showReservationExpiredNotification")
        val pendingIntent = SplashActivity.intent(context).let { splashIntent ->
            PendingIntent.getActivity(
                context,
                0,
                splashIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
            )
        }

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(context.getString(R.string.reservation_expire_title))
            .setContentText(context.getString(R.string.reservation_expire_body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, notification)
        }
    }

    fun getNotificationType(intent: Intent): NotificationType? =
        when (val hi = intent.getStringExtra(NOTIFICATION_TYPE_KEY)) {
            NotificationType.ALMOST_EXPIRED.name -> {
                Timber.d("-_-_- 1 hi is $hi")
                NotificationType.ALMOST_EXPIRED
            }
            NotificationType.EXPIRED.name -> {
                Timber.d("-_-_- 2 hi is $hi")
                NotificationType.EXPIRED
            }
            else -> null
        }

    fun setFutureReservationExpirationNotification(
        context: Context,
        endAt: Time,
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

        val (expiredIntent, almostExpiredIntent) = createExpirationPendingIntents(context)

        val expireAt = LocalDateTime.of(
            LocalDate.now(),
            LocalTime.of(endAt.hour, endAt.minute, 0, 0),
        )
        val almostExpireAt = expireAt.minus(5, ChronoUnit.MINUTES)

        val zoneOffset = OffsetDateTime.now().offset

        val expireAtMillis = expireAt.toInstant(zoneOffset).toEpochMilli()
        val almostExpireAtMillis = almostExpireAt.toInstant(zoneOffset).toEpochMilli()

        alarmManager?.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            expireAtMillis,
            expiredIntent
        )

        alarmManager?.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            almostExpireAtMillis,
            almostExpiredIntent,
        )
    }

    fun cancelExpirationNotifications(context: Context) {
        val (expiredIntent, almostExpiredIntent) = createExpirationPendingIntents(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmManager?.cancel(expiredIntent)
        alarmManager?.cancel(almostExpiredIntent)
    }

    private fun createExpirationPendingIntents(context: Context): Pair<PendingIntent, PendingIntent> {
        val expiredIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(NOTIFICATION_TYPE_KEY, NotificationType.EXPIRED.name)
            PendingIntent.getBroadcast(context, 0, intent, 0)
        }
        val almostExpiredIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            intent.putExtra(NOTIFICATION_TYPE_KEY, NotificationType.ALMOST_EXPIRED.name)
            PendingIntent.getBroadcast(context, 1, intent, 0)
        }
        return expiredIntent to almostExpiredIntent
    }
}

enum class NotificationType {
    EXPIRED, ALMOST_EXPIRED;
}
