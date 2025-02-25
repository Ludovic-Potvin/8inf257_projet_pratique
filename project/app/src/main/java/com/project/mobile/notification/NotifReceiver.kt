package com.project.mobile.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.project.mobile.presentation.Priorite

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: "Titre par défaut"
        val message = intent?.getStringExtra("message") ?: "Message par défaut"
        val prioString = intent?.getStringExtra("priorite") ?: "MOYENNE" // Valeur par défaut
        val prio = Priorite.valueOf(prioString) // Convertir en enum

        // Afficher la notification
        showNotification(context, title, message, prio)
    }
}
