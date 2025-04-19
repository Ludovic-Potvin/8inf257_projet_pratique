package com.project.mobile.presentation.list

import ReminderHeader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.mobile.navigation.Screen
import com.project.mobile.ui.component.StoryCard

@Composable
fun ListStoriesScreen(
    navController: NavController,
    viewModel: ListStoriesViewModel = hiltViewModel()
) {
    val temperatures = viewModel.temperatures.value

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

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
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
                        navController.navigate(Screen.AddEditStoryScreen.route)
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
                        contentDescription = "Add a story"
                    )
                }
            }
        }
    }
}
