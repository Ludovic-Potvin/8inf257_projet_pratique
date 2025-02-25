package com.project.mobile.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.project.mobile.common.PriorityType

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: "Titre par défaut"
        val message = intent?.getStringExtra("message") ?: "Message par défaut"
        val prioInt = intent?.getIntExtra("priorite", 2) ?: 2 // 2 = StandardPriority par défaut
        val prio = PriorityType.fromInt(prioInt)

        // Afficher la notification
        showNotification(context, title, message, prio)
    }
}
