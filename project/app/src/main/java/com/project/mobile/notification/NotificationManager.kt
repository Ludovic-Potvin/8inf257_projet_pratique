package com.project.mobile.notification

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.project.mobile.data.Story
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import javax.inject.Inject

class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
) : ViewModel() {
    private fun instantNotification(title: String, text: String) {
        if (hasNotificationPermission()) {
            try {
                notificationManager.notify(
                    1,
                    notificationBuilder
                        .setContentTitle(title)
                        .setContentText(text)
                        .build()
                )
            } catch (e: SecurityException) { e.printStackTrace() }
        }
    }

    private fun nextTriggerTime(dayOfWeek: DayOfWeek, hour: Int, minute: Int): Long {
        val now = LocalDateTime.now()
        var nextTime = now.with(TemporalAdjusters.nextOrSame(dayOfWeek))
            .withHour(hour)
            .withMinute(minute)

        if (nextTime.isBefore(now)) {
            nextTime = nextTime.plusWeeks(1)
        }

        return nextTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun setNotification(story: Story) {
        if (hasNotificationPermission()) {
            val time = story.hour.split(':')

            val dayMap = mapOf(
                0 to DayOfWeek.SUNDAY,
                1 to DayOfWeek.MONDAY,
                2 to DayOfWeek.TUESDAY,
                3 to DayOfWeek.WEDNESDAY,
                4 to DayOfWeek.THURSDAY,
                5 to DayOfWeek.FRIDAY,
                6 to DayOfWeek.SATURDAY
            )

            for (i in 0 until 7) {
                if (story.days[i] == '1') {
                    planNotification(story, dayMap[i]!!, time[0].toInt(), time[1].toInt())
                }
            }
            instantNotification(title = "Notification crée", text = "Notification crée")
        }
    }

    private fun planNotification(story: Story, dayOfWeek: DayOfWeek, hour: Int, minute: Int)
    {
        val triggerTime = nextTriggerTime(dayOfWeek, hour, minute)

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("title", story.title)
            putExtra("description", story.description)
            putExtra("priority", story.priority)
            putExtra("category", story.category)
        }

        val requestCode = story.id!! * 10 + dayOfWeek.value
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            AlarmManager.INTERVAL_DAY * 7,
            pendingIntent
        )
    }

    fun cancelNotification(story: Story) {
        val dayMap = mapOf(
            0 to DayOfWeek.SUNDAY,
            1 to DayOfWeek.MONDAY,
            2 to DayOfWeek.TUESDAY,
            3 to DayOfWeek.WEDNESDAY,
            4 to DayOfWeek.THURSDAY,
            5 to DayOfWeek.FRIDAY,
            6 to DayOfWeek.SATURDAY
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (i in 0 until 7) {
            if (story.days[i] == '0') {
                val day = dayMap[i]!!
                val requestCode = story.id!! * 10 + day.value

                val intent = Intent(context, NotificationReceiver::class.java).apply {
                    putExtra("title", story.title)
                    putExtra("description", story.description)
                }

                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )

                alarmManager.cancel(pendingIntent)
            }
        }
        instantNotification(title = "Notification supprimée", text = "Notification supprimée")
    }


    private fun hasNotificationPermission(): Boolean {
        val hasPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

        val areNotificationsEnabled = NotificationManagerCompat.from(context).areNotificationsEnabled()
        return hasPermission && areNotificationsEnabled
    }
}