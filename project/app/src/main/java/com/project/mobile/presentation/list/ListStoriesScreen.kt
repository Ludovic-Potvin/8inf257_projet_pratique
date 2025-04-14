package com.project.mobile.presentation.list

import ReminderHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.project.mobile.navigation.Screen
import com.project.mobile.ui.component.StoryCard

@Composable
fun ListStoriesScreen(
    navController: NavController, viewModel: ListStoriesViewModel = hiltViewModel()
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                ReminderHeader()
            }

            val temperatures = viewModel.temperatures.value

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 0.dp, horizontal = 30.dp)
            ) {
                items(viewModel.stories.value) { story ->
                    StoryCard(
                        story = story,
                        temperatures = temperatures,
                        onClick = {
                            navController.navigate(
                                Screen.AddEditStoryScreen.route + "?storyId=${story.id}"
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(11.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp)) // espace entre stories et trait

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
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(80.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Edit parameters"
                    )
                }

                Button(
                    onClick = {
                        navController.navigate(Screen.AddEditStoryScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.tertiary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.width(80.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add a story"
                    )
                }
            }
        }
    }
}




