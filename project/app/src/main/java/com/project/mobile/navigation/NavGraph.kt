package com.project.mobile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.mobile.data.StoriesDatabase
import com.project.mobile.presentation.addedit.AddEditStoryScreen
import com.project.mobile.presentation.list.ListStoriesScreen
import com.project.mobile.presentation.preference.PreferenceScreen
import com.project.mobile.ui.theme.ThemeViewModel

@Composable
fun NavGraph(db: StoriesDatabase,
             themeViewModel: ThemeViewModel,
             navController: NavHostController,
             innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.StoriesListScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.StoriesListScreen.route) {
            ListStoriesScreen(navController)
        }
        composable(route = Screen.AddEditStoryScreen.route + "?storyId={storyId}",
            arguments = listOf(
                navArgument(name = "storyId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditStoryScreen(navController)
        }
        composable(route = Screen.PreferenceScreen.route) {
            PreferenceScreen(navController, themeViewModel)
        }
    }
}