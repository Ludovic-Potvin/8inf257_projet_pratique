package com.project.mobile.presentation

import Activated
import NoActivated
import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.mobile.navigation.Screen
import com.project.mobile.ui.theme.DarkPurple
import com.project.mobile.ui.theme.Purple
import com.project.mobile.ui.theme.WhitePurple
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.project.mobile.utils.DataStoreManager
import kotlinx.coroutines.launch
import suezOneRegular
import trocchi

@Composable
fun RoutineFormPage(navController: NavController, dataStoreManager: DataStoreManager, context: Context, routineId: String?) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "REMINDER",
                modifier = Modifier.padding(vertical = 10.dp),
                color = Purple,
                fontSize = 36.sp,
                fontFamily = suezOneRegular,
                textAlign = TextAlign.Center
            )

            Spacer(
                modifier = Modifier.height(1.dp)
                    .background(Purple)
                    .width(260.dp)
            )
            RoutineForm(
                navController = navController,
                dataStoreManager = dataStoreManager,
                context = context,
                routineId = routineId,
                modifier = Modifier.padding(innerPadding),
            )
        }

    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun RoutineForm(navController: NavController, dataStoreManager: DataStoreManager, context: Context, routineId: String?, modifier: Modifier = Modifier) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDays by remember { mutableStateOf(linkedMapOf("lundi" to DayVM("L", "Lundi", NoActivated),
        "mardi" to DayVM("M", "Mardi", NoActivated),
        "mercredi" to DayVM("M", "Mercredi",NoActivated),
        "jeudi" to DayVM("J", "Jeudi",NoActivated),
        "vendredi" to DayVM("V", "Vendredi", NoActivated),
        "samedi" to DayVM("S", "Samedi", NoActivated),
        "dimanche" to DayVM("D", "Dimande", NoActivated))
    ) }
    var hour by remember { mutableStateOf(LocalTime.now()) }
    var showError by remember { mutableStateOf(false) }
    Log.d("RoutineformPage","routineid =$routineId")

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
            Text(
                "Titre :",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = trocchi
            )
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
                selectedDays.forEach { (key, day) ->
                    val isSelected = day.state.activated

                    Box(
                        modifier = Modifier
                            .clickable {
                                selectedDays = if (!isSelected) {
                                    LinkedHashMap(selectedDays).apply {
                                        put(
                                            key,
                                            day.copy(state = Activated)
                                        ) // Mettre à jour l'état du jour
                                    }
                                } else {
                                    LinkedHashMap(selectedDays).apply {
                                        //remove(day.key) // Retirer le jour de la liste7
                                        put(key, day.copy(state = NoActivated))
                                    }
                                }
                        }
                            .size(32.dp)
                            .background(color = day.state.backgroundColor,
                                shape = CircleShape)
                            .border(1.dp, WhitePurple, CircleShape),
                            contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.abreviation, fontFamily = trocchi, fontSize = 16.sp,
                            color = Color.White
                        )
                    }
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
                colors = ButtonColors(
                    disabledContainerColor = WhitePurple,
                    disabledContentColor = Color.White,
                    containerColor = WhitePurple,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Annuler", fontFamily = trocchi, color = DarkPurple, fontWeight = FontWeight.Bold)
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
            }, colors = ButtonColors(
                disabledContainerColor = Purple,
                disabledContentColor = Color.White,
                containerColor = Purple,
                contentColor = Color.White
            )) {
                Text(text = "Enregistrer", fontFamily = trocchi, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
    }

}


