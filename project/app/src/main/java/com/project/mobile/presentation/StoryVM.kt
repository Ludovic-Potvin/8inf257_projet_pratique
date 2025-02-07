package com.project.mobile.presentation
import androidx.compose.runtime.remember
import java.time.LocalTime
data class StoryVM(
    val title: String = "",
    val description: String = "",
    val days: List<String> = listOf(),
    val hour: LocalTime = LocalTime.now()
)

val stories =
    mutableListOf(
        StoryVM(
            title = "Faire du sport",
            description = "Séance de musculation",
            days = listOf("L", "M", "V"),
            hour = LocalTime.of(18, 30)
        )
)
