package com.project.mobile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.project.mobile.presentation.components.StoryCard
import com.project.mobile.ui.theme.Purple
import suezOneRegular

//TODO This is where we want to display the list of routine available
@Composable
fun RoutineListPage(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
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

            Spacer(modifier = Modifier.height(1.dp)
                .background(Purple)
                .width(260.dp)
            )

            LazyColumn(
                modifier = Modifier.padding(innerPadding)
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 0.dp, horizontal = 30.dp)
            ) {
                stories.forEach { story ->
                    item {
                        StoryCard(story) {
                            stories.remove(story)
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(1.dp)
                .background(Purple)
                .fillMaxWidth())

            Button(onClick = {
                navController.navigate("routine_form/1")
            }, colors = ButtonColors(
                disabledContainerColor = Purple,
                disabledContentColor = Color.White,
                containerColor = Purple,
                contentColor = Color.White),
                modifier = Modifier.padding(top = 10.dp)
                    .width(80.dp)
            ) {
                Text(text = "+",
                    style = TextStyle(fontFamily = suezOneRegular,
                        fontSize = 20.sp)
                )
            }
        }
    }
}


