package com.project.mobile.presentation.addedit

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.project.mobile.data.Story
import com.project.mobile.notification.NotificationManager
import java.time.DayOfWeek


class FakeNotificationManager(
    context: Context,
    builder: NotificationCompat.Builder,
    notificationManager: NotificationManagerCompat
) : NotificationManager(context, builder, notificationManager) {

    val addedNotification = mutableListOf<Story>()

    override fun setNotification(story: Story) {
        addedNotification.removeIf { it.id == story.id }
        addedNotification.add(story)
    }

    override fun cancelNotification(story: Story) {
        addedNotification.remove(story)
    }

}
