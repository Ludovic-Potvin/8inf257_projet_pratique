package com.project.mobile

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
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
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            StoriesDatabase::class.java,
            StoriesDatabase.DATABASE_NAME
        ).build()
    }

    override fun attachBaseContext(newBase: Context) {
        val langCode = runBlocking {
            newBase.dataStore.data.first()[PreferencesKeys.LANGUAGE] ?: "en"
        }
        super.attachBaseContext(newBase.updateLocale(langCode))
    }

    private fun Context.updateLocale(languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale)
            createConfigurationContext(config)
        } else {
            @Suppress("DEPRECATION")
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
            this
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
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