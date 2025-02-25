package com.project.mobile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.mobile.presentation.list.RoutineListPage
import com.project.mobile.presentation.RoutineFormPage
import com.project.mobile.viewmodel.ListStoriesViewModel

@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.StoriesListScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.StoriesListScreen.route) {
            val stories = viewModel<ListStoriesViewModel>()
            RoutineListPage(navController, dataStoreManager)
        }
        composable(route = Screen.FormStoryScreen.route) { backStackEntry ->
            RoutineFormPage(
                navController,
                routineId = backStackEntry.arguments?.getString("routineId"),
                context = backStackEntry.localContext,
                dataStoreManager = dataStoreManager
            )
        }
    }
}