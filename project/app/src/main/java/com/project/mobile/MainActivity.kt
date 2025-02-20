package com.project.mobile


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.mobile.ui.theme.MobileprojectTheme
import androidx.navigation.compose.rememberNavController
import com.project.mobile.presentation.list.ListStoriesViewModel
import com.project.mobile.presentation.list.RoutineListPage
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.project.mobile.navigation.Screen
import com.project.mobile.presentation.RoutineFormPage
import com.project.mobile.presentation.form.ModifRoutineForm
import com.project.mobile.utils.DataStoreManager
import com.project.mobile.utils.ListStoriesViewModelFactory
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileprojectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 1. Crée le navController
                    val navController = rememberNavController()
                    val dataStoreManager = DataStoreManager(this)
               /*     val coroutineScope = rememberCoroutineScope()
                    coroutineScope.launch {
                        dataStoreManager.clearDataStore()
                    }
                */

                    // 3. Mappe toutes les pages à une route dans le navController
                    NavHost(
                        navController = navController,
                        startDestination = Screen.StoriesListScreen.route
                    ) {
                        composable(route = Screen.StoriesListScreen.route) {
                            RoutineListPage(navController, dataStoreManager)
                        }
                        composable(route = Screen.FormStoryScreen.route) { backStackEntry ->
                            RoutineFormPage(
                                navController,
                                routineId = backStackEntry.arguments?.getString("routineId"),
                                context = this@MainActivity,
                                dataStoreManager = dataStoreManager
                            )
                        }
                        composable(route = Screen.ModifStoryScreen.route) { backStackEntry ->
                            val routineId = backStackEntry.arguments?.getString("routineId")?.toIntOrNull()
                            if (routineId != null) {
                                ModifRoutineForm(
                                    navController = navController,
                                    dataStoreManager = dataStoreManager,
                                    context = this@MainActivity,
                                    routineId = routineId // Passer l'ID à ModifRoutineFormPage
                                )
                            }
                        }

                            }
                    }


                }
            }
        }
}
