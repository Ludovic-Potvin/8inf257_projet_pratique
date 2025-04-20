package com.project.mobile.navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

sealed class Screen(val route: String) {
    object RoutinesListScreen : Screen(route = "routines_list_screen")
    object AddEditRoutineScreen : Screen(route = "add_edit_routines_screen")
    object PreferenceScreen: Screen(route = "preference_screen")
}
