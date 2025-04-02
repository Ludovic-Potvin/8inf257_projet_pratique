package com.project.mobile.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.project.mobile.common.PriorityType

fun showNotification(context: Context, title: String, message: String, prio: PriorityType) {
    val channelId = "notification_channel"
    val channelName = "Notification Channel"

    val priorite = when(prio)
    {
        PriorityType.LowPriority -> NotificationCompat.PRIORITY_LOW
        PriorityType.StandardPriority -> NotificationCompat.PRIORITY_DEFAULT
        PriorityType.HighPriority -> NotificationCompat.PRIORITY_HIGH
    }

    // Créer un canal de notification pour Android 8.0+
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    // Construire et afficher la notification
    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle(title)
        .setContentText(message)
        .setPriority(priorite)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .build()

    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(1, notification)
}