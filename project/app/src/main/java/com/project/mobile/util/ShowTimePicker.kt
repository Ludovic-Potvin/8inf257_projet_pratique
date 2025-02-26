package com.project.mobile.util

import android.app.TimePickerDialog
import android.content.Context
import java.time.LocalTime

fun showTimePicker(context: Context, initialTime: LocalTime, onTimeSelected: (LocalTime) -> Unit) {
    val timePicker = TimePickerDialog(
        context,
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