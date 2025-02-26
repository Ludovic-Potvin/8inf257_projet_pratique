package com.project.mobile.presentation.addedit

import com.project.mobile.common.CategoryType
import com.project.mobile.common.PriorityType
import java.time.LocalTime

sealed interface AddEditStoryEvent {
    data class EnteredTitle(val title: String): AddEditStoryEvent
    data class EnteredDescription(val description: String): AddEditStoryEvent
    data class EnteredCategory(val category: CategoryType): AddEditStoryEvent
    data class EnteredHour(val hour: LocalTime): AddEditStoryEvent
    data class EnteredDay(val index: Int): AddEditStoryEvent
    data class EnteredPriority(val priority: PriorityType): AddEditStoryEvent
    data object SaveStory: AddEditStoryEvent
    data object DeleteStory: AddEditStoryEvent
}