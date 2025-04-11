package com.project.mobile.presentation.preference

import com.project.mobile.common.CategoryType
import com.project.mobile.common.PriorityType
import java.time.LocalTime

sealed interface PreferenceEvent {
    data class EnteredTheme(val theme: Int): PreferenceEvent
}