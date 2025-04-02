
package com.project.mobile.notification

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.pm.PackageManager
import android.app.NotificationManager

@RequiresApi(Build.VERSION_CODES.S)
fun checkAndRequestPermissions(context: Context): Boolean {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    var hasAlarmPermission = true
    var hasNotificationPermission = true

    if (!alarmManager.canScheduleExactAlarms()) {
        Toast.makeText(
            context,
            "Autorisez les alarmes exactes dans les paramètres de l'application.",
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
            data = android.net.Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
        hasAlarmPermission = false
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(
                context,
                "Autorisez les notifications pour cette application.",
                Toast.LENGTH_LONG
            ).show()

            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            }
            context.startActivity(intent)
            hasNotificationPermission = false
        }
    }

    return hasAlarmPermission && hasNotificationPermission
}