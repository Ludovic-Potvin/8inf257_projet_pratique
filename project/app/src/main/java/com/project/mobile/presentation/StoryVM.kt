package com.project.mobile.presentation

import java.time.LocalTime
import kotlin.random.Random

data class StoryVM (
    val id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val hour: LocalTime = LocalTime.now(),
    //list ou map
    val days: LinkedHashMap<String,DayVM> = linkedMapOf("lundi" to DayVM("L", "Lundi", NoActivated),
                            "mardi" to DayVM("M", "Mardi", NoActivated),
                            "mercredi" to DayVM("M", "Mercredi",NoActivated),
                            "jeudi" to DayVM("J", "Jeudi",Activated),
                            "vendredi" to DayVM("V", "Vendredi", NoActivated),
                            "samedi" to DayVM("S", "Samedi", NoActivated),
                            "dimanche" to DayVM("D", "Dimande", NoActivated))
/*    val days: LinkedHashMap<String, DayVM> = linkedMapOf("lundi" to DayVM("L", NoActivated),
        "mardi" to DayVM("M", NoActivated),
        "mercredi" to DayVM("M", NoActivated),
        "jeudi" to DayVM("J", Activated),
        "vendredi" to DayVM("V", NoActivated),
        "samedi" to DayVM("S", NoActivated),
        "dimanche" to DayVM("D", NoActivated)),*/
)
