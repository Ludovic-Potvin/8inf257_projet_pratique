package com.project.mobile.presentation

import android.content.Context
import com.project.mobile.utils.DataStoreManager
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class StoryVM(
    val id: Int,
    val title: String = "",
    val description: String = "",
    val categorie: String = "Autre",
    val hour: String="00:00",
    val days: LinkedHashMap<String, DayVM> = Days.days,
    /*    val days: LinkedHashMap<String, DayVM> = linkedMapOf("lundi" to DayVM("L", NoActivated),
          "mardi" to DayVM("M", NoActivated),
          "mercredi" to DayVM("M", NoActivated),
          "jeudi" to DayVM("J", Activated),
          "vendredi" to DayVM("V", NoActivated),
          "samedi" to DayVM("S", NoActivated),
          "dimanche" to DayVM("D", NoActivated)),*/
) {
    companion object {
        suspend fun create(context: Context): StoryVM {
            val dataStoreManager = DataStoreManager(context)
            val newId = dataStoreManager.generateNewStoryId()
            return StoryVM(id = newId)
        }
    }
    fun getHourAsLocalTime(): LocalTime {
        return try {
            LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            LocalTime.MIDNIGHT // Si erreur, retourne 00:00
        }
    }
}


