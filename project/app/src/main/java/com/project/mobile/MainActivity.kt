package com.project.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.mobile.ui.theme.MobileprojectTheme
import androidx.navigation.compose.rememberNavController
import com.project.mobile.ui.RoutineFormPage
import com.project.mobile.ui.RoutineListPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileprojectTheme {
                // 1. Create the navController
                val navController = rememberNavController()

                // 2. Map all pages to route in the navController
                NavHost(navController = navController, startDestination = "routine_list") {
                    composable("routine_list") {
                        RoutineListPage(navController)
                    }
                    composable("routine_form/{routineId}") { backStackEntry ->
                        RoutineFormPage(navController, routineId = backStackEntry.arguments?.getString("routineId"))
                    }
                }
            }
        }
    }
}