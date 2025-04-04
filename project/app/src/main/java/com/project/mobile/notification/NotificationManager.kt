package com.project.mobile.notification

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.project.mobile.data.Story
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val notificationBuilder: NotificationCompat.Builder,
    private val notificationManager: NotificationManagerCompat
) : ViewModel() {
    fun setNotification(story: Story) {
        if (hasNotificationPermission()) {
            try {
                notificationManager.notify(
                    story.id!!,
                    notificationBuilder
                        .setContentTitle(story.title)
                        .setContentText(story.description)
                        .build()
                )
            } catch (e: SecurityException) { e.printStackTrace() }
        }
    }

    fun cancelNotification(story: Story) {
        notificationManager.cancel(story.id!!)
    }

    private fun hasNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else { true }
    }
}