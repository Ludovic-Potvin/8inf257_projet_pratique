package com.project.mobile

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: "Titre par défaut"
        val message = intent?.getStringExtra("message") ?: "Message par défaut"

        // Afficher la notification
        showNotification(context, title, message)
    }
}
