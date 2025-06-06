package com.project.mobile.presentation.list

import com.project.mobile.language.LanguageViewModel
import ReminderHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.mobile.navigation.Screen
import com.project.mobile.ui.component.RoutineCard

@Composable
fun ListRoutinesScreen(navController: NavController, viewModel: ListRoutinesViewModel, languageViewModel: LanguageViewModel = hiltViewModel()) {
    val currentLanguage = languageViewModel.currentLanguage
    val temperatures = viewModel.temperatures.value

    Scaffold(modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ReminderHeader()
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 0.dp, horizontal = 30.dp)
                ) {
                    items(viewModel.routines.value) { routine ->
                        RoutineCard(
                            routine,
                            temperatures = temperatures,
                            onClick = {
                                navController.navigate(
                                    Screen.AddEditRoutineScreen.route + "?routineId=${routine.id}"
                                );
                            },
                            currentLanguage = currentLanguage
                        )
                        Spacer(modifier = Modifier.height(11.dp))
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(colorScheme.tertiary)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Screen.PreferenceScreen.route)
                        }, colors = ButtonColors(
                            disabledContainerColor = colorScheme.tertiary,
                            disabledContentColor = Color.White,
                            containerColor = colorScheme.tertiary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .width(80.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Edit parameters"
                        )
                    }

                    Button(
                        onClick = {
                            navController.navigate(Screen.AddEditRoutineScreen.route)
                        }, colors = ButtonColors(
                            disabledContainerColor = colorScheme.tertiary,
                            disabledContentColor = Color.White,
                            containerColor = colorScheme.tertiary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .width(80.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add a routine"
                        )
                    }
                }
            }
        }
    }
}



