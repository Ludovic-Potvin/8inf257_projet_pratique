package com.project.mobile.util

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Context
import android.content.ContextWrapper
import java.time.LocalTime

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
fun showTimePicker(context: Context, initialTime: LocalTime, onTimeSelected: (LocalTime) -> Unit) {
    val activity = context.findActivity()
    if (activity == null || activity.isFinishing) {
        println("Activity context is not valid")
        return
    }

    val timePicker = TimePickerDialog(
        activity,
        { _, selectedHour, selectedMinute ->
            val newTime = LocalTime.of(selectedHour, selectedMinute).withSecond(0).withNano(0)
            onTimeSelected(newTime)
        },
        initialTime.hour,
        initialTime.minute,
        true
    )
    timePicker.show()
}
