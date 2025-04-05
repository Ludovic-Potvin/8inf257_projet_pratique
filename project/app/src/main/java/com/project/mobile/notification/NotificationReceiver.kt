package com.project.mobile.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver  : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val priority = intent.getIntExtra("priority", 2)
        val category = intent.getStringExtra("category")

        val notificationBuilder = NotificationCompat.Builder(context, "Main channel ID")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(priority)
            .setCategory(category)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify((System.currentTimeMillis() % 10000).toInt(), notificationBuilder.build())
    }
}