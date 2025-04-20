package com.project.mobile.presentation.addedit

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.project.mobile.data.Routine
import com.project.mobile.notification.NotificationManager
import java.time.DayOfWeek


class FakeNotificationManager(
    context: Context,
    builder: NotificationCompat.Builder,
    notificationManager: NotificationManagerCompat
) : NotificationManager(context, builder, notificationManager) {

    val addedNotification = mutableListOf<Routine>()

    override fun setNotification(routine: Routine) {
        addedNotification.removeIf { it.id == routine.id }
        addedNotification.add(routine)
    }

    override fun cancelNotification(routine: Routine) {
        addedNotification.remove(routine)
    }

}
