package com.project.mobile.presentation

import java.time.LocalTime
import kotlin.random.Random

data class StoryVM (
    val id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val hour: LocalTime = LocalTime.now(),
    //Ou mettre une map ?
    val days: MutableList<DayVM> = mutableListOf(DayVM("L", "Lundi", NoActivated),
                            DayVM("M", "Mardi", NoActivated),
                            DayVM("M", "Mercredi",NoActivated),
                            DayVM("J", "Jeudi",Activated),
                            DayVM("V", "Vendredi", NoActivated),
                            DayVM("S", "Samedi", NoActivated),
                            DayVM("D", "Dimande", NoActivated))
)
