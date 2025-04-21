package com.project.mobile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.project.mobile.data.RoutinesDatabase
import com.project.mobile.presentation.addedit.AddEditRoutineScreen
import com.project.mobile.presentation.list.ListRoutinesScreen
import com.project.mobile.presentation.list.ListRoutinesViewModel
import com.project.mobile.presentation.preference.PreferenceScreen
import com.project.mobile.ui.theme.ThemeViewModel

@Composable
fun NavGraph(db: RoutinesDatabase,
             themeViewModel: ThemeViewModel,
             navController: NavHostController,
             innerPadding: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = Screen.RoutinesListScreen.route,
        modifier = Modifier.padding(innerPadding)
    ) {
        composable(route = Screen.RoutinesListScreen.route) {
            ListRoutinesScreen(
                navController,
                viewModel = hiltViewModel(),
                languageViewModel = hiltViewModel()
            )
        }
        composable(route = Screen.AddEditRoutineScreen.route + "?routineId={routineId}",
            arguments = listOf(
                navArgument(name = "routineId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditRoutineScreen(navController)
        }
        composable(route = Screen.PreferenceScreen.route) {
            PreferenceScreen(navController, themeViewModel)
        }
    }
}