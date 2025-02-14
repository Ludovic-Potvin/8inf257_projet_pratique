package com.project.mobile.notification

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.S)
fun checkAndRequestExactAlarmPermission(context: Context): Boolean {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // Check if the app has permission to schedule exact alarms
    if (!alarmManager.canScheduleExactAlarms()) {
        // Notify the user and redirect to settings
        Toast.makeText(
            context,
            "Autorisez les alarmes exactes dans les paramètres de l'application.",
            Toast.LENGTH_LONG
        ).show()

        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
            data = android.net.Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
        return false
    }

    return true
}
