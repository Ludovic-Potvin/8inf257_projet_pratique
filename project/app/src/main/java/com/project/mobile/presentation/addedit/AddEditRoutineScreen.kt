package com.project.mobile.presentation.addedit
import com.project.mobile.R
import ReminderHeader
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.mobile.navigation.Screen
import com.project.mobile.ui.component.CategoryDropdownMenu
import com.project.mobile.ui.component.DayCard
import com.project.mobile.ui.component.PriorityDropdownMenu
import com.project.mobile.language.LanguageViewModel
import com.project.mobile.util.showTimePicker
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.collectLatest
import trocchi
import java.util.Locale
import androidx.compose.material3.MaterialTheme.colorScheme as theme

@Composable
fun AddEditRoutineScreen(navController: NavController,
                       viewModel: AddEditRoutineViewModel = hiltViewModel(),
                       languageViewModel: LanguageViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val currentLanguage = languageViewModel.currentLanguage

    // Configuration de la locale
    val configuration = remember { Configuration(context.resources.configuration) }
    configuration.setLocale(Locale(currentLanguage))
    val localizedContext = remember(configuration) { context.createConfigurationContext(configuration) }

    CompositionLocalProvider(LocalContext provides localizedContext) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            LaunchedEffect(true) {
                viewModel.eventFlow.collectLatest { event ->
                    when (event) {
                        is AddEditRoutineUiEvent.SavedRoutine -> {
                            navController.navigate(Screen.RoutinesListScreen.route)
                        }
                        is AddEditRoutineUiEvent.ShowMessage -> {
                            snackbarHostState.showSnackbar(event.message)
                        }
                        is AddEditRoutineUiEvent.DeletedRoutine -> {
                            navController.navigate(Screen.RoutinesListScreen.route)
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ReminderHeader()
                RoutineForm(
                    navController = navController,
                    viewModel = viewModel,
                    currentLanguage = currentLanguage
                )
            }

        }
    }
}

@Composable
fun RoutineForm(
    navController: NavController, viewModel: AddEditRoutineViewModel = hiltViewModel(),
    currentLanguage: String
) {
    val routine = viewModel.routine.value
    val context = LocalContext.current
    val temperatures = viewModel.temperatures.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(theme.secondary)
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

                Box(
                    modifier = Modifier
                        .clickable { viewModel.onEvent(AddEditRoutineEvent.DeleteRoutine) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_button_desc),
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Title
            Text(
                stringResource(R.string.title_label),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )
            OutlinedTextField(
                value = routine.title,
                onValueChange = {
                    viewModel.onEvent(AddEditRoutineEvent.EnteredTitle(it))
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = theme.tertiary,
                    unfocusedContainerColor = theme.tertiary,
                    disabledContainerColor = theme.tertiary,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Days
            Text(
                stringResource(R.string.days_label),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val dayInitials = stringArrayResource(R.array.day_initials)
                routine.days.forEachIndexed { index, day ->
                    val temp = temperatures.getOrNull(index)
                    val tempText = temp?.toInt()?.toString()

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        DayCard(
                            label = dayInitials[index],
                            isActive = day,
                            onClick = {
                                viewModel.onEvent(AddEditRoutineEvent.EnteredDay(index))
                            }
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = tempText?.let { "$it°C" } ?: "--",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = trocchi
                            )
                        )
                    }
                }
            }



            Spacer(modifier = Modifier.height(8.dp))

            // Sélecteur d'heure
            Text(
                stringResource(R.string.time_label),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )

            OutlinedTextField(
                value = routine.hour.format(DateTimeFormatter.ofPattern("HH:mm")),
                onValueChange = {},
                label = { Text(stringResource(R.string.time_placeholder)) },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = theme.tertiary,
                    unfocusedContainerColor = theme.tertiary,
                    disabledContainerColor = theme.tertiary,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        showTimePicker(
                            context,
                            routine.getHourAsLocalTime()
                        ) { newTime ->
                            viewModel.onEvent(AddEditRoutineEvent.EnteredHour(newTime))
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = stringResource(R.string.time_label),
                            tint = Color.White
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                stringResource(R.string.description_label),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )
            OutlinedTextField(
                value = routine.description,
                onValueChange = {
                    viewModel.onEvent(AddEditRoutineEvent.EnteredDescription(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = theme.tertiary,
                    unfocusedContainerColor = theme.tertiary,
                    disabledContainerColor = theme.tertiary,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                stringResource(R.string.category_label),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )

            CategoryDropdownMenu(
                selectedCategory = routine.category,
                onCategorySelected = { newCategory ->
                    viewModel.onEvent(AddEditRoutineEvent.EnteredCategory(newCategory))
                },
                currentLanguage = currentLanguage
            )

            Spacer(modifier = Modifier.height(8.dp))
            //Priorite
            Text(
                stringResource(R.string.priority_label),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )

            PriorityDropdownMenu(
                selectedPriority = routine.priority
            ) { newPriority ->
                viewModel.onEvent(AddEditRoutineEvent.EnteredPriority(newPriority))
            }

            // Boutons Enregistrer et Annuler
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate(Screen.RoutinesListScreen.route) },
                    colors = ButtonColors(
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.White,
                        containerColor = Color.White,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(3.dp)
                ) {
                    Text(
                        text = stringResource(R.string.button_cancel),
                        fontFamily = trocchi,
                        color = theme.secondary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        viewModel.onEvent(AddEditRoutineEvent.SaveRoutine)
                    },
                    colors = ButtonColors(
                        disabledContainerColor = theme.secondary,
                        disabledContentColor = Color.White,
                        containerColor = theme.tertiary,
                        contentColor = Color.White
                    ), elevation = ButtonDefaults.buttonElevation(3.dp)) {
                    Text(text = stringResource(R.string.button_save), fontFamily = trocchi, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


