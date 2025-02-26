package com.project.mobile.presentation.list

import ReminderHeader
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.mobile.navigation.Screen
import com.project.mobile.presentation.addedit.AddEditStoryViewModel
import com.project.mobile.ui.component.StoryCard
import com.project.mobile.ui.theme.Purple

@Composable
fun ListStoriesScreen(navController: NavController, viewModel: ListStoriesViewModel) {

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
                        story,
                        onClick = {
                            Screen.AddEditStoryScreen.route + "?storyId=${story.id}"
                        }
                    )
                    Spacer(modifier = Modifier.height(11.dp))
                }
            }

            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .background(Purple)
                    .fillMaxWidth()
            )

            Button(
                onClick = {
                    navController.navigate(Screen.AddEditStoryScreen.route)
                }, colors = ButtonColors(
                    disabledContainerColor = Purple,
                    disabledContentColor = Color.White,
                    containerColor = Purple,
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



