package com.project.mobile.presentation

import NoActivated

data class Days (
    val days: LinkedHashMap<String,DayVM> = linkedMapOf("lundi" to DayVM("L", "Lundi", NoActivated),
        "mardi" to DayVM("M", "Mardi", NoActivated),
        "mercredi" to DayVM("M", "Mercredi",NoActivated),
        "jeudi" to DayVM("J", "Jeudi",NoActivated),
        "vendredi" to DayVM("V", "Vendredi", NoActivated),
        "samedi" to DayVM("S", "Samedi", NoActivated),
        "dimanche" to DayVM("D", "Dimande", NoActivated))

    ) {
    companion object {
        val days: LinkedHashMap<String, DayVM>
            get() {
                return days
            }
    }
}