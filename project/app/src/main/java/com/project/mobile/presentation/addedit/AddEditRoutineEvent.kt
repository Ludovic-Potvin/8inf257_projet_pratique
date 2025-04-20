package com.project.mobile.presentation.addedit

import com.project.mobile.common.CategoryType
import com.project.mobile.common.PriorityType
import java.time.LocalTime

sealed interface AddEditRoutineEvent {
    data class EnteredTitle(val title: String): AddEditRoutineEvent
    data class EnteredDescription(val description: String): AddEditRoutineEvent
    data class EnteredCategory(val category: CategoryType): AddEditRoutineEvent
    data class EnteredHour(val hour: LocalTime): AddEditRoutineEvent
    data class EnteredDay(val index: Int): AddEditRoutineEvent
    data class EnteredPriority(val priority: PriorityType): AddEditRoutineEvent
    data object SaveRoutine: AddEditRoutineEvent
    data object DeleteRoutine: AddEditRoutineEvent
}