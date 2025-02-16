package com.project.mobile.presentation.form

import Activated
import NoActivated
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.mobile.MainActivity
import com.project.mobile.navigation.Screen
import com.project.mobile.presentation.DayVM
import com.project.mobile.presentation.Days
import com.project.mobile.ui.theme.Purple
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.project.mobile.presentation.StoryVM
import com.project.mobile.utils.DataStoreManager
import kotlinx.coroutines.launch

@Composable
fun ModifRoutineForm(navController: NavHostController, dataStoreManager: DataStoreManager, context: MainActivity, routineId: Int) {
    // Charger les données de la routine à modifier
    val coroutineScope = rememberCoroutineScope()
    var showError by remember { mutableStateOf(false) }
    var story by remember { mutableStateOf<StoryVM?>(null) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf(LocalTime.now()) }
    var selectedDays by remember { mutableStateOf(LinkedHashMap<String, DayVM>()) }

    // Charger les données de la routine à modifier
    LaunchedEffect(routineId) {
        // Récupérer la story à partir de l'id
        story = dataStoreManager.getStoryById(routineId)
        story?.let {
            title = it.title
            description = it.description
            hour = it.hour
            selectedDays = it.days
        }
     Log.d("ModifRoutineForm", " story=$story , routine id=$routineId")
    }

        // Interface de formulaire
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

                // Champ Description
                Text("Description :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
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

                Spacer(modifier = Modifier.height(8.dp))

                // Sélection des jours (identique à votre logique précédente)
                Text("Jour :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    selectedDays.forEach { (key, day) ->
                        val isSelected = day.state.activated
                        Button(
                            onClick = {
                                selectedDays = if (!isSelected) {
                                    LinkedHashMap(selectedDays).apply {
                                        put(key, day.copy(state = Activated)) // Mettre à jour l'état du jour
                                    }
                                } else {
                                    LinkedHashMap(selectedDays).apply {
                                        put(key, day.copy(state = NoActivated)) // Retirer le jour de la liste
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
                            Text(text = day.abreviation)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Champ Heure
                Text("Heure :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                OutlinedTextField(
                    value = hour.format(DateTimeFormatter.ofPattern("HH:mm")),
                    onValueChange = { /* Peut implémenter un Picker d'heure si nécessaire */ },
                    modifier = Modifier.fillMaxWidth(),
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            val newRoutine = StoryVM(
                                id = routineId,
                                title = title,
                                description = description,
                                days = selectedDays,
                                hour = hour)

                            coroutineScope.launch {
                            dataStoreManager.deleteStory(newRoutine)
                            navController.navigate(Screen.StoriesListScreen.route) }
                                  },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = "supprimer")
                    } // Bouton de soumission
                    Button(onClick = {
                        if (title.isBlank()) {
                            showError = true
                        } else {
                            showError = false
                            val newRoutine = StoryVM(
                                id = routineId,
                                title = title,
                                description = description,
                                days = selectedDays,
                                hour = hour
                            )

                            // Lancer une coroutine pour enregistrer la routine
                            coroutineScope.launch {
                                dataStoreManager.addOrUpdateStory(story = newRoutine)
                                navController.navigate(Screen.StoriesListScreen.route) // Naviguer après l'enregistrement
                            }
                        }
                    }) {
                        Text(text = "Enregistrer")
                    }
                }
            }
        }

}