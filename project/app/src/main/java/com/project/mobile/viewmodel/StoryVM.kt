package com.project.mobile.viewmodel

import com.project.mobile.data.model.Story
import com.project.mobile.common.CategoryType
import com.project.mobile.common.PriorityType
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random


data class StoryVM(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val category: CategoryType = CategoryType.AUTRE,
    val hour: String = "00:00",
    val days: String = "0000000", // 7 char, each represent a day starting from sunday. 0 = false, 1 = true
    val priority: PriorityType = PriorityType.StandardPriority
) {
    companion object {
        fun fromEntity(entity: Story) : StoryVM {
            return StoryVM(
                id = entity.id!!,
                title = entity.title,
                description = entity.description,
                category = CategoryType.fromLabel(entity.category),
                hour = entity.hour,
                days = entity.days,
                priority = PriorityType.fromInt(entity.priority)
            )
        }
    }

    fun getHourAsLocalTime(): LocalTime {
        return try {
            LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            LocalTime.MIDNIGHT
        }
    }
}


