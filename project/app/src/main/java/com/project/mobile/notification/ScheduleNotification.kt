package com.project.mobile.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.SharedPreferences
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

fun cancelNotification(context: Context, jour: String, hour: Int, minute: Int) {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationReceiver::class.java)
    val requestCode = jour.hashCode() + hour * 100 + minute
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        requestCode,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    alarmManager.cancel(pendingIntent)
}

fun scheduleNotification(context: Context, jour: String, hour: Int, minute: Int, titreNotif: String, messageNotif: String) {
    try {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("title", titreNotif)
            putExtra("message", messageNotif)
        }

        val requestCode = jour.hashCode() + hour * 100 + minute
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
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

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY * 7, // Répéter toutes les semaines
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

fun saveNotificationTime(context: Context, jour: String, hour: Int, minute: Int) {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE)
    with(sharedPreferences.edit()) {
        putInt("${jour}_hour", hour)
        putInt("${jour}_minute", minute)
        apply()
    }
}

fun getPreviousNotificationTime(context: Context, jour: String): Pair<Int, Int>? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE)
    val hour = sharedPreferences.getInt("${jour}_hour", -1)
    val minute = sharedPreferences.getInt("${jour}_minute", -1)

    return if (hour != -1 && minute != -1) Pair(hour, minute) else null
}