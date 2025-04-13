package com.project.mobile

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.project.mobile.data.StoriesDatabase
import com.project.mobile.navigation.NavGraph
import com.project.mobile.ui.theme.MobileprojectTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

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
            MobileprojectTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavGraph(db, navController, innerPadding)
                }
            }
        }
    }
}

@Composable
fun MobileApp(db: StoriesDatabase) {
    MobileprojectTheme {
        val navController = rememberNavController()
        Scaffold { innerPadding ->
            NavGraph(
                navController = navController,
                db = db,
                innerPadding = innerPadding
            )
        }
    }
}

object PreferencesKeys {
    val LANGUAGE = stringPreferencesKey("app_language")
}

val Context.dataStore by preferencesDataStore("settings")