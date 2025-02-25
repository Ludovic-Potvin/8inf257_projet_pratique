package com.project.mobile.presentation.form

import Activated
import NoActivated
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.mobile.MainActivity
import com.project.mobile.navigation.Screen
import com.project.mobile.presentation.Categorie
import com.project.mobile.notification.cancelNotification
import com.project.mobile.notification.getPreviousNotificationTime
import com.project.mobile.notification.saveNotificationTime
import com.project.mobile.notification.scheduleNotificationWithPermission
import com.project.mobile.presentation.DayVM
import com.project.mobile.presentation.Priorite
import com.project.mobile.ui.theme.Purple
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import com.project.mobile.viewmodel.StoryVM
import com.project.mobile.ui.theme.DarkPurple
import com.project.mobile.utils.DataStoreManager
import kotlinx.coroutines.launch
import suezOneRegular
import trocchi

@Composable
fun ModifRoutineForm(navController: NavHostController, dataStoreManager: DataStoreManager, context: MainActivity, routineId: Int) {
    // Charger les données de la routine à modifier
    val coroutineScope = rememberCoroutineScope()
    var showTitleError by remember { mutableStateOf(false) }
    var showDayError by remember { mutableStateOf(false) }
    var story by remember { mutableStateOf<StoryVM?>(null) }
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var hour by remember { mutableStateOf(LocalTime.now()) }

    var selectedDays by remember { mutableStateOf(LinkedHashMap<String, DayVM>()) }
    var expanded by remember { mutableStateOf(false) }
    var categorie by remember { mutableStateOf(Categorie.AUTRE) }
    var priorite by remember { mutableStateOf(Priorite.MOYENNE) }
    var expandedPriorite by remember { mutableStateOf(false) }

    // Charger les données de la routine à modifier
    LaunchedEffect(routineId) {
        // Récupérer la story à partir de l'id
        story = dataStoreMagsnager.getStoryById(routineId)
        story?.let {
            title = it.title
            description = it.description
            hour = LocalTime.parse(it.hour, DateTimeFormatter.ofPattern("HH:mm"))
            selectedDays = it.days
            categorie = it.categorie
            priorite = it.priorite
        }
     Log.d("ModifRoutineForm", " story=$story , routine id=$routineId")
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                    Box(
                        modifier = Modifier
                            .clickable { navController.navigate(Screen.StoriesListScreen.route) }
                            .align(Alignment.Start)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    // Champ Titre
                    Text("Titre :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = trocchi)
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
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
                    if (showTitleError) {
                        Text(
                            text = "Veuillez saisir un titre",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))


                    // Sélection des jours (identique à votre logique précédente)
                    Text("Jour :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = trocchi)
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
                                    .border(1.dp, Color.White, CircleShape),
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

                    // Ajout du message d'erreur si aucun jour n'est sélectionné
                    if (showDayError) {
                        Text(
                            text = "Veuillez sélectionner au moins un jour",
                            color = Color.Red,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Champ Heure
                    Text("Heure :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = trocchi)
                    OutlinedTextField(
                        value = hour.format(DateTimeFormatter.ofPattern("HH:mm")),
                        onValueChange = {},
                        readOnly = true,
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
                        },
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

                    // Champ Description
                    Text("Description :", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = trocchi)
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

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Catégorie :",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = trocchi
                    )
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier
                            .border(1.dp,Color.White, shape = RoundedCornerShape(5.dp))
                    ) {
                        OutlinedTextField(
                            value = categorie.label,
                            textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .clickable { expanded = !expanded }
                                .background(Purple, shape = RoundedCornerShape(5.dp))
                                .border(1.dp,Color.White, shape = RoundedCornerShape(5.dp))
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },

                            modifier = Modifier.background(Purple)
                        ) {
                            Categorie.values().forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.label, color=Color.White, fontSize = 16.sp) },
                                    onClick = {
                                        categorie = option
                                        expanded = false
                                    }
                                )
                            }
                        }
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

                    ExposedDropdownMenuBox(
                        expanded = expandedPriorite,
                        onExpandedChange = { expandedPriorite = !expandedPriorite },
                        modifier = Modifier
                            .border(1.dp, Color.White, shape = RoundedCornerShape(5.dp))
                    ) {

                        OutlinedTextField(
                            value = priorite.label,
                            textStyle = TextStyle(fontSize = 16.sp, color = Color.White),
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPriorite)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                                .clickable { expandedPriorite = !expandedPriorite }
                                .background(Purple, shape = RoundedCornerShape(5.dp))
                                .border(1.dp, Color.White, shape = RoundedCornerShape(5.dp))
                        )

                        ExposedDropdownMenu(
                            expanded = expandedPriorite,
                            onDismissRequest = { expandedPriorite = false },
                            modifier = Modifier.background(Purple)
                        ) {
                            Priorite.values().forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option.label, color = Color.White, fontSize = 16.sp) },
                                    onClick = {
                                        priorite = option
                                        expandedPriorite = false
                                    }
                                )
                            }
                        }
                    }
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
                                    hour = hour.toString()
                                )

                                coroutineScope.launch {
                                dataStoreManager.deleteStory(newRoutine)
                                navController.navigate(Screen.StoriesListScreen.route) }

                                for (jour in selectedDays.values)
                                {
                                    cancelNotification(context, jour.fullname, hour.hour, hour.minute)
                                }
                            },
                            colors = ButtonColors(
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.White,
                                containerColor = Color.White,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(3.dp)
                        ) {
                            Text(text = "Supprimer", fontFamily = trocchi, color = DarkPurple, fontWeight = FontWeight.Bold)
                        } // Bouton de soumission
                        Button(onClick = {
                            showTitleError = title.isBlank()
                            showDayError = selectedDays.values.none { it.state.activated }
                            // Si aucune erreur, on enregistre
                            if (!showTitleError && !showDayError) {
                                val newRoutine = StoryVM(
                                    id = routineId,
                                    title = title,
                                    description = description,
                                    days = selectedDays,
                                    hour = hour.toString(),
                                    categorie = categorie,
                                    priorite = priorite
                                )

                                // Lancer une coroutine pour enregistrer la routine
                                coroutineScope.launch {
                                    dataStoreManager.addOrUpdateStory(story = newRoutine)
                                    navController.navigate(Screen.StoriesListScreen.route) // Naviguer après l'enregistrement
                                }

                                for (jour in selectedDays.values) {

                                    val previousTime = getPreviousNotificationTime(context, jour.fullname)
                                    if (previousTime != null) {
                                        cancelNotification(context, jour.fullname, previousTime.first, previousTime.second) // Supprime l'ancienne alarme
                                    }

                                    if (jour.state == Activated)
                                    {
                                        scheduleNotificationWithPermission(context, jour.fullname, hour.hour, hour.minute, title, description)
                                        saveNotificationTime(context, jour.fullname, hour.hour, hour.minute)
                                    }
                                    else
                                    {
                                        cancelNotification(context, jour.fullname, hour.hour, hour.minute)
                                    }
                                }
                            }
                        }, colors = ButtonColors(
                            disabledContainerColor = Purple,
                            disabledContentColor = Color.White,
                            containerColor = Purple,
                            contentColor = Color.White
                        ),elevation = ButtonDefaults.buttonElevation(3.dp)) {
                            Text(text = "Enregistrer", fontFamily = trocchi, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

        }
    }
}