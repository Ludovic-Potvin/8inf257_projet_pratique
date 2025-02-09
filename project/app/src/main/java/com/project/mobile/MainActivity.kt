package com.project.mobile


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.mobile.ui.theme.MobileprojectTheme
import androidx.navigation.compose.rememberNavController
import com.project.mobile.navigation.Screen
import com.project.mobile.presentation.form.RoutineFormPage
import com.project.mobile.presentation.list.ListStoriesViewModel
import com.project.mobile.presentation.list.RoutineListPage
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileprojectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 1. Create the navController
                    val navController = rememberNavController()

                    // 2. Map all pages to route in the navController
                    NavHost(
                        navController = navController,
                        startDestination = Screen.StoriesListScreen.route
                    ) {
                        composable(route = Screen.StoriesListScreen.route) {
                            val stories = viewModel<ListStoriesViewModel>()
                            RoutineListPage(navController, stories)
                        }
                        composable(route = Screen.FormStoryScreen.route) { backStackEntry ->
                            RoutineFormPage(
                                navController,
                                routineId = backStackEntry.arguments?.getString("routineId")
                            )
                        }
                    }

                    // 3. Once finished, redirect to routine list page
                    /* LaunchedEffect(Unit) {
                    navController.navigate("routine_list")
                }*/
                }
            }
        }
    }
}