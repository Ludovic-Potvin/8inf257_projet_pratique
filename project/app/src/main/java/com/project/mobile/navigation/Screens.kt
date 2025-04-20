package com.project.mobile.navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

sealed class Screen(val route: String) {
    object StoriesListScreen : Screen(route = "stories_list_screen")
    object AddEditRoutineScreen : Screen(route = "add_edit_stories_screen")
    object PreferenceScreen: Screen(route = "preference_screen")
}
