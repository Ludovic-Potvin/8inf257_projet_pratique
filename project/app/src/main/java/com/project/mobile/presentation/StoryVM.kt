package com.project.mobile.presentation

import java.time.LocalTime
import kotlin.random.Random

data class StoryVM (
    val id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val hour: LocalTime = LocalTime.now(),
    val days: MutableList<DayVM> = mutableListOf(DayVM("L", NoActivated),
                            DayVM("M", NoActivated),
                            DayVM("M", NoActivated),
                            DayVM("J", Activated),
                            DayVM("V", NoActivated),
                            DayVM("S", NoActivated),
                            DayVM("D", NoActivated))
)
