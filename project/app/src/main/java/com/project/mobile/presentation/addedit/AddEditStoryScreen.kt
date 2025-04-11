package com.project.mobile.presentation.addedit

import ReminderHeader
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.material3.MaterialTheme.colorScheme as theme
import com.project.mobile.util.showTimePicker
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.flow.collectLatest
import trocchi

@Composable
fun AddEditStoryScreen(navController: NavController, viewModel: AddEditStoryViewModel = hiltViewModel()) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        LaunchedEffect(true) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is AddEditStoryUiEvent.SavedStory -> {
                        navController.navigate(Screen.StoriesListScreen.route)
                    }
                    is AddEditStoryUiEvent.ShowMessage -> {
                        snackbarHostState.showSnackbar(event.message)
                    }
                    is AddEditStoryUiEvent.DeletedStory -> {
                        navController.navigate(Screen.StoriesListScreen.route)
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
        ){
            ReminderHeader()
            RoutineForm(navController)
        }
    }
}

@Composable
fun RoutineForm(navController: NavController, viewModel: AddEditStoryViewModel = hiltViewModel()) {
    val story = viewModel.story.value
    val context = LocalContext.current
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
                        .clickable { navController.navigate(Screen.StoriesListScreen.route) }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clickable { viewModel.onEvent(AddEditStoryEvent.DeleteStory) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Title
            Text(
                "Titre :",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )
            OutlinedTextField(
                value = story.title,
                onValueChange = {
                    viewModel.onEvent(AddEditStoryEvent.EnteredTitle(it))
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
                "Jours :",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                story.days.forEachIndexed { index, day ->
                    DayCard(
                        label="SMTWTFS"[index].toString(),
                        isActive=day,
                        onClick= {
                            viewModel.onEvent(AddEditStoryEvent.EnteredDay(index))
                        })
                    Spacer(modifier = Modifier.width(4.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sélecteur d'heure
            Text(
                "Heure :",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )

            OutlinedTextField(
                value = story.hour.format(DateTimeFormatter.ofPattern("HH:mm")),
                onValueChange = {},
                label = { Text("HH:MM") },
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
                            story.getHourAsLocalTime()
                        ) { newTime ->
                            viewModel.onEvent(AddEditStoryEvent.EnteredHour(newTime))
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = "Sélectionner l'heure",
                            tint = Color.White
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                "Description",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )
            OutlinedTextField(
                value = story.description,
                onValueChange = {
                    viewModel.onEvent(AddEditStoryEvent.EnteredDescription(it))
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
                "Catégorie :",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )

            CategoryDropdownMenu(
                selectedCategory = story.category
            ) { newCategory ->
                viewModel.onEvent(AddEditStoryEvent.EnteredCategory(newCategory))
            }

            Spacer(modifier = Modifier.height(8.dp))
            //Priorite
            Text(
                "Priorité :",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )

            PriorityDropdownMenu(
                selectedPriority = story.priority
            ) { newPriority ->
                viewModel.onEvent(AddEditStoryEvent.EnteredPriority(newPriority))
            }

            // Boutons Enregistrer et Annuler
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate(Screen.StoriesListScreen.route) },
                    colors = ButtonColors(
                        disabledContainerColor = Color.White,
                        disabledContentColor = Color.White,
                        containerColor = Color.White,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(3.dp)
                ) {
                    Text(text = "Annuler", fontFamily = trocchi, color = theme.secondary, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        viewModel.onEvent(AddEditStoryEvent.SaveStory)
                    },
                    colors = ButtonColors(
                    disabledContainerColor = theme.secondary,
                    disabledContentColor = Color.White,
                    containerColor = theme.tertiary,
                    contentColor = Color.White
                ), elevation = ButtonDefaults.buttonElevation(3.dp)) {
                    Text(text = "Enregistrer", fontFamily = trocchi, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}


