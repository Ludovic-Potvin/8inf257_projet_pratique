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
import androidx.room.Room
import com.project.mobile.data.database.StoriesDatabase
import com.project.mobile.navigation.NavGraph

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
            MobileprojectTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(navController, innerPadding)
                }
            }
        }
    }
}
