package com.project.mobile.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.project.mobile.data.Story
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar

class NotificationManager(context: Context)
{
    val _context = context

    fun scheduleNotificationWithPermission(story: Story)
    {
        if (checkAndRequestPermissions(_context))
        {
            scheduleNotification(_context, story)
        }
    }

    private fun scheduleNotification(context: Context, story: Story)
    {
        for (i in 0 until 7)
        {
            if (story.days[i] == '1')
            {
                createNotification(context, story, i)
            }
            else
            {
                removeNotification(context, story)
            }
        }
    }

    private fun createNotification(context: Context, story: Story, day: Int)
    {
        try {
            val time = story.hour.split(':')
            val hour = time[0].toInt()
            val minute = time[1].toInt()

            val joursMap = mapOf(
                0 to Calendar.SUNDAY,
                1 to Calendar.MONDAY,
                2 to Calendar.TUESDAY,
                3 to Calendar.WEDNESDAY,
                4 to Calendar.THURSDAY,
                5 to Calendar.FRIDAY,
                6 to Calendar.SATURDAY
            )

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)

                val targetDay = joursMap[day] ?: Calendar.MONDAY

                val daysUntilTarget = (targetDay - get(Calendar.DAY_OF_WEEK) + 7) % 7
                if (daysUntilTarget > 0) {
                    add(Calendar.DAY_OF_YEAR, daysUntilTarget)
                } else if (daysUntilTarget == 0 && timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.DAY_OF_YEAR, 7)
                }
            }

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NotificationReceiver::class.java).apply {
                putExtra("title", story.title + " (" + story.category + ")")
                putExtra("message", story.description)
            }

            val requestCode = story.id
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode!!,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY * 7,
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

    private fun removeNotification(context: Context, story: Story)
    {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationReceiver::class.java)
        val requestCode = story.id
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode!!,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.cancel(pendingIntent)
    }
}