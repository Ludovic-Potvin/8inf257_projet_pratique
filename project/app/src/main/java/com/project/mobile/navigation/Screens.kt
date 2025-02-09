package com.project.mobile.navigation

sealed class Screen (val route: String) {
    data object StoriesListScreen : Screen(route = "stories_list_screen")
    data object FormStoryScreen : Screen(route = "routine_form/{routineId}")
}