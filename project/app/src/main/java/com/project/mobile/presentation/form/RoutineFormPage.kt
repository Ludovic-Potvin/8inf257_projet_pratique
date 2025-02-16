package com.project.mobile.presentation

import Activated
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.mobile.navigation.Screen
import com.project.mobile.ui.theme.Purple
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.project.mobile.utils.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun RoutineFormPage(navController: NavController, dataStoreManager: DataStoreManager, context: Context, routineId: String?) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        RoutineForm(
            navController = navController,
            dataStoreManager = dataStoreManager,
            context = context,
            routineId = routineId,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun RoutineForm(navController: NavController, dataStoreManager: DataStoreManager, context: Context, routineId: String?, modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDays by remember { mutableStateOf(LinkedHashMap<String, DayVM>()) }
    var hour by remember { mutableStateOf(LocalTime.now()) }
    var showError by remember { mutableStateOf(false) }
    Log.d("RoutineformPage","routineid =$routineId")
    // Créer une instance de StoryVM avec la liste des jours
    val days: Days = Days()

    // Accès aux jours dans StoryVM
    val daysList: Days = days

    // Créer un CoroutineScope avec remember
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Purple)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Champ Titre
            Text("Titre :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                isError = showError,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Purple,
                    unfocusedContainerColor = Purple,
                    disabledContainerColor = Purple,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )
            if (showError) {
                Text(
                    text = "Le titre ne peut pas être vide",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sélection des jours
            Text("Jour :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                daysList.days.forEach { day ->
                    val isSelected = selectedDays[day.key]?.state?.activated ?: false
                    Button(
                        onClick = {
                            selectedDays = if (!isSelected) {
                                LinkedHashMap(selectedDays).apply {
                                    put(day.key, day.value.copy(state = Activated)) // Mettre à jour l'état du jour
                                }
                            } else {
                                LinkedHashMap(selectedDays).apply {
                                    remove(day.key) // Retirer le jour de la liste
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color.White else Color(0x99FFFFFF),
                            contentColor = if (isSelected) Purple else Color.White
                        ),
                        shape = CircleShape,
                        modifier = Modifier
                            .padding(2.dp)
                            .size(42.dp)
                            .clip(CircleShape)
                    ) {
                        Text(text = day.value.fullname, color = if (isSelected) Purple else Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sélecteur d'heure
            Text("Heure :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = hour.format(DateTimeFormatter.ofPattern("HH:mm")),
                onValueChange = {},
                label = { Text("HH:MM") },
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Purple,
                    unfocusedContainerColor = Purple,
                    disabledContainerColor = Purple,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        val timePicker = TimePickerDialog(
                            context,
                            { _, selectedHour, selectedMinute ->
                                hour = LocalTime.of(selectedHour, selectedMinute)
                            },
                            hour.hour,
                            hour.minute,
                            true
                        )
                        timePicker.show()
                    }) {
                        Icon(imageVector = Icons.Filled.AccessTime, contentDescription = "Sélectionner l'heure", tint = Color.White)
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text("Description", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Purple,
                    unfocusedContainerColor = Purple,
                    disabledContainerColor = Purple,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    disabledTextColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Boutons Enregistrer et Annuler
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { navController.navigate(Screen.StoriesListScreen.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(text = "Annuler")
                }

                Button(onClick = {
                    if (title.isBlank()) {
                        showError = true
                    } else {
                        showError = false
                        coroutineScope.launch {
                            val newId = dataStoreManager.generateNewStoryId()

                            val newRoutine = StoryVM(
                                id = newId,
                                title = title,
                                description = description,
                                days = selectedDays,
                                hour = hour
                            )

                            dataStoreManager.addOrUpdateStory(newRoutine)
                            navController.navigate(Screen.StoriesListScreen.route)
                        }
                    }
                }) {
                    Text(text = "Enregistrer")
                }
            }
        }
    }
}


