package com.project.mobile.viewmodel

import com.project.mobile.data.Routine
import com.project.mobile.common.CategoryType
import com.project.mobile.common.PriorityType
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random
data class RoutineVM(
    val id: Int = Random.nextInt(),
    val title: String = "",
    val description: String = "",
    val category: CategoryType = CategoryType.AUTRE,
    val hour: String = "00:00",
    val days: List<Boolean> = List(7) { false },
    val priority: PriorityType = PriorityType.StandardPriority
) {
    companion object {
        fun fromEntity(entity: Routine): RoutineVM {
            return RoutineVM(
                id = entity.id!!,
                title = entity.title,
                description = entity.description,
                category = CategoryType.fromLabel(entity.category),
                hour = entity.hour,
                days = entity.days.map { it == '1' },
                priority = PriorityType.fromInt(entity.priority)
            )
        }
    }

    fun toEntity(): Routine {
        val id = if (this.id == -1) null else this.id
        return Routine(
            id = id,
            title = this.title,
            description = this.description,
            category = this.category.name, // Utilisez le nom de l'enum au lieu du label
            hour = this.hour,
            days = this.days.joinToString("") { if (it) "1" else "0" },
            priority = this.priority.toInt()
        )
    }

    fun getHourAsLocalTime(): LocalTime {
        return try {
            LocalTime.parse(hour, DateTimeFormatter.ofPattern("HH:mm"))
        } catch (e: Exception) {
            LocalTime.MIDNIGHT
        }
    }
}