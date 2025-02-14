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
import androidx.compose.ui.Modifier
import com.project.mobile.navigation.Screen
import com.project.mobile.presentation.RoutineFormPage
import com.project.mobile.utils.DataStoreManager
import com.project.mobile.utils.ListStoriesViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileprojectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // 1. Crée le navController
                    val navController = rememberNavController()
                    val dataStoreManager = DataStoreManager(this)

                    // 2. Configure le ViewModel avec un factory personnalisé
                    val viewModelFactory = ListStoriesViewModelFactory(dataStoreManager)

                    // 3. Mappe toutes les pages à une route dans le navController
                    NavHost(
                        navController = navController,
                        startDestination = Screen.StoriesListScreen
                    ) {
                        composable(route = Screen.StoriesListScreen.route) {
                            // Utilisation du factory personnalisé pour créer le ViewModel
                            val storiesViewModel: ListStoriesViewModel = viewModel(factory = viewModelFactory)
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
                    }

                    // 3. Une fois terminé, redirige vers la page de la liste des routines
                    /* LaunchedEffect(Unit) {
                        navController.navigate("routine_list")
                    }*/
                }
            }
        }
    }
}
