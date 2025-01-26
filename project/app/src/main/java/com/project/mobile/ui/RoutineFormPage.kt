package com.project.mobile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RoutineFormPage(navController: NavController, routineId: String?) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        RoutineForm(
            navController = navController,
            name = "RoutineFormPage",
            routineId = routineId,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun RoutineForm(navController: NavController, name: String, routineId: String?, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), // optional padding
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hello $name! We are editing $routineId",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = {
            navController.navigate("routine_list")
        }) {
            Text(text = "Go to RoutineListPage")
        }
    }
}