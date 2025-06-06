package com.project.mobile.presentation.preference

import LanguageSwitcher
import ReminderHeader
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.mobile.R
import com.project.mobile.language.LanguageViewModel
import com.project.mobile.navigation.Screen
import com.project.mobile.ui.component.ColorCard
import com.project.mobile.ui.theme.DarkPurplePrimary
import com.project.mobile.ui.theme.LightBlue
import com.project.mobile.ui.theme.LightGreen
import com.project.mobile.ui.theme.LightOrange
import com.project.mobile.ui.theme.LightPurple
import com.project.mobile.ui.theme.OutlinePurple
import com.project.mobile.ui.theme.ThemeViewModel
import com.project.mobile.ui.theme.WhiteBlue
import com.project.mobile.ui.theme.WhiteGreen
import com.project.mobile.ui.theme.WhiteOrange
import com.project.mobile.ui.theme.WhitePurple
import androidx.compose.material3.MaterialTheme.colorScheme as theme
import trocchi
import java.util.Locale

@Composable
fun PreferenceScreen(navController: NavController, viewModel: ThemeViewModel) {
    val languageViewModel: LanguageViewModel = hiltViewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val currentLanguage = languageViewModel.currentLanguage
    val configuration = remember { Configuration(context.resources.configuration) }
    configuration.setLocale(Locale(currentLanguage))
    val localizedContext = remember(configuration) { context.createConfigurationContext(configuration) }

    CompositionLocalProvider(LocalContext provides localizedContext) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ReminderHeader()
                PreferenceForm(navController, viewModel, languageViewModel)
            }
        }
    }
}
@Composable
fun PreferenceForm(
    navController: NavController,
    viewModel: ThemeViewModel,
    languageViewModel: LanguageViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(theme.tertiary)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clickable { navController.navigate(Screen.RoutinesListScreen.route) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_desc),
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
                Text(
                    stringResource(R.string.preference_title),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = trocchi
                )
            }
            Spacer(modifier = Modifier.height(18.dp))

            // Theme
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    stringResource(R.string.theme_label),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = trocchi
                )

                ColorCard(
                    onClick= {
                        viewModel.onEvent(PreferenceEvent.EnteredTheme(0))
                    },
                    color = LightPurple,
                    outline = WhitePurple
                )

                ColorCard(
                    onClick= {
                        viewModel.onEvent(PreferenceEvent.EnteredTheme(1))
                    },
                    color = DarkPurplePrimary,
                    outline = OutlinePurple
                )

                ColorCard(
                    onClick= {
                        viewModel.onEvent(PreferenceEvent.EnteredTheme(2))
                    },
                    color = LightGreen,
                    outline = WhiteGreen
                )

                ColorCard(
                    onClick= {
                        viewModel.onEvent(PreferenceEvent.EnteredTheme(3))
                    },
                    color = LightOrange,
                    outline = WhiteOrange
                )

                ColorCard(
                    onClick= {
                        viewModel.onEvent(PreferenceEvent.EnteredTheme(4))
                    },
                    color = LightBlue,
                    outline = WhiteBlue
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Language
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    stringResource(R.string.language_label),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = trocchi
                )

                LanguageSwitcher(
                    onLanguageSelected = { code ->
                        languageViewModel.setLanguage(code)
                    },
                    currentLanguage = languageViewModel.currentLanguage
                )
            }
        }
    }
}
