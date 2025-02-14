package com.project.mobile.navigation

import kotlinx.serialization.Serializable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json

// @Polymorphic pour dire que cette classe et ses sous-classes seront sérialisées
@Polymorphic
@Serializable
sealed class Screen(val route: String) {

    @Serializable
    @SerialName("StoriesListScreen") // Nom pour la sérialisation
    object StoriesListScreen : Screen(route = "stories_list_screen")

    @Serializable
    @SerialName("FormStoryScreen") // Nom pour la sérialisation
    object FormStoryScreen : Screen(route = "routine_form/{routineId}")
}
