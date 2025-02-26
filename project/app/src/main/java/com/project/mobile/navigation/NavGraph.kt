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
import com.project.mobile.presentation.addedit.AddEditStoryViewModel
import com.project.mobile.presentation.list.ListStoriesScreen
import com.project.mobile.presentation.list.ListStoriesViewModel

@Composable
fun NavGraph(db: StoriesDatabase, navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.StoriesListScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.StoriesListScreen.route) {
            val stories = viewModel<ListStoriesViewModel>(){
                ListStoriesViewModel(db.dao)
            }
            ListStoriesScreen(navController, stories)
        }
        composable(route = Screen.AddEditStoryScreen.route + "?storyId={storyId}",
            arguments = listOf(
                navArgument(name = "storyId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { backStackEntry ->
            val storyId = backStackEntry.arguments?.getInt("storyId") ?: -1
            val story = viewModel<AddEditStoryViewModel>() {
                AddEditStoryViewModel(db.dao, storyId)
            }
            AddEditStoryScreen(navController, story)
        }
    }
}