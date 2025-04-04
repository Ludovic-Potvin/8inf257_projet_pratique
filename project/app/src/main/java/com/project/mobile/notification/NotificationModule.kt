package com.project.mobile.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NotificationModule
{
    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ) : NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val channel = NotificationChannel(
                "Main channel ID",
                "Main channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        return notificationManager
    }

    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ) : NotificationCompat.Builder {
        return NotificationCompat.Builder(context, "Main channel ID")
            .setContentTitle("Welcome")
            .setContentText("Stories App")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    }
}
