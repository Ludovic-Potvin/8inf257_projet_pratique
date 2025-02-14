package com.project.mobile

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.mobile.notification.scheduleNotificationWithPermission
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.LaunchedEffect
import com.project.mobile.ui.theme.MobileprojectTheme
import androidx.navigation.compose.rememberNavController
import com.project.mobile.ui.RoutineFormPage
import com.project.mobile.ui.RoutineListPage

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scheduleNotificationWithPermission(this, "Jeudi", 14, 50, "Bonjour", "Message")
        enableEdgeToEdge()
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

                // 3. Once finished, redirect to routine list page
                LaunchedEffect(Unit) {
                    navController.navigate("routine_list")
                }
            }
        }
    }
}