package com.project.mobile
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import com.project.mobile.ui.theme.MobileprojectTheme
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.room.Room
import com.project.mobile.data.StoriesDatabase
import com.project.mobile.navigation.NavGraph
import com.project.mobile.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Database
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            StoriesDatabase::class.java,
            StoriesDatabase.DATABASE_NAME
        ).build()
    }

    // Interface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            MobileprojectTheme(themeViewModel) {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(db, themeViewModel, navController, innerPadding)
                }
            }
        }
    }
}
