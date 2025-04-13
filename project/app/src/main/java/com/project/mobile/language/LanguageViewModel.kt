package com.project.mobile.language

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.content.edit
import android.content.res.Configuration
import android.os.Build
import android.provider.Settings.Global.putString
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.project.mobile.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val application: Application,
    private val sharedPrefs: SharedPreferences
) : ViewModel() {

    var currentLanguage by mutableStateOf(sharedPrefs.getString("app_language", "en") ?: "en")
        private set

    fun setLanguage(languageCode: String) {
        currentLanguage = languageCode
        sharedPrefs.edit { putString("app_language", languageCode) }

        // Force la langue sur tout l'APP
        updateAppLanguage()
    }

    // Fonction principale pour appliquer la langue
    private fun updateAppLanguage() {
        val locale = Locale(currentLanguage)
        Locale.setDefault(locale)

        val resources = application.resources
        val config = resources.configuration
        config.setLocale(locale)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            application.createConfigurationContext(config)
        }
    }
}