package com.project.mobile

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.S)
fun scheduleNotificationWithPermission(context: Context, hour: Int, minute: Int) {
    if (checkAndRequestExactAlarmPermission(context)) {
        scheduleNotification(context, hour, minute) // Call your scheduling function
    }
}

fun scheduleNotification(context: Context, hour: Int, minute: Int) {
    try {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("title", "Rappel")
            putExtra("message", "C'est l'heure de la notification !")
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    } catch (e: SecurityException) {
        Toast.makeText(
            context,
            "Failed to schedule exact alarm: ${e.message}",
            Toast.LENGTH_LONG
        ).show()
    }
}

