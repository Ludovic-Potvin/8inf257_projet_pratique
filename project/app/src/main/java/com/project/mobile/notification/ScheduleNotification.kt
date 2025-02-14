package com.project.mobile.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.util.*

@RequiresApi(Build.VERSION_CODES.S)
fun scheduleNotificationWithPermission(context: Context, jour: String, hour: Int, minute: Int, titreNotif: String, messageNotif: String) {
    if (checkAndRequestExactAlarmPermission(context)) {
        scheduleNotification(context, jour, hour, minute, titreNotif, messageNotif) // Call your scheduling function
    }
}

fun scheduleNotification(context: Context, jour: String, hour: Int, minute: Int, titreNotif: String, messageNotif: String) {
    try {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("title", titreNotif)
            putExtra("message", messageNotif)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Mapping entre le nom du jour en français et la constante Calendar
        val joursMap = mapOf(
            "Lundi" to Calendar.MONDAY,
            "Mardi" to Calendar.TUESDAY,
            "Mercredi" to Calendar.WEDNESDAY,
            "Jeudi" to Calendar.THURSDAY,
            "Vendredi" to Calendar.FRIDAY,
            "Samedi" to Calendar.SATURDAY,
            "Dimanche" to Calendar.SUNDAY
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            // Récupérer la constante Calendar correspondant au jour
            val targetDay = joursMap[jour] ?: Calendar.MONDAY // Par défaut, lundi si inconnu

            // Calculer le nombre de jours jusqu'au jour cible
            val daysUntilTarget = (targetDay - get(Calendar.DAY_OF_WEEK) + 7) % 7
            if (daysUntilTarget > 0) {
                add(Calendar.DAY_OF_YEAR, daysUntilTarget)
            } else if (daysUntilTarget == 0 && timeInMillis < System.currentTimeMillis()) {
                // Si aujourd'hui est le bon jour mais l'heure est déjà passée, programmer pour la semaine suivante
                add(Calendar.DAY_OF_YEAR, 7)
            }
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

