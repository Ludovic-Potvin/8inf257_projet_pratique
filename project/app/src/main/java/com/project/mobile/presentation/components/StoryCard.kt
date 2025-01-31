package com.project.mobile.presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mobile.presentation.StoryVM
import com.project.mobile.ui.theme.Purple
import suezOneRegular

@Composable
fun StoryCard(story: StoryVM, onDeleteClick: (StoryVM) -> Unit){
    Box(
    modifier = Modifier
        .fillMaxSize()
        .background(Purple)
        .padding(15.dp, 12.dp),
    ){
        Column (modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Text(story.title,
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    fontFamily = suezOneRegular
                ))
            Text(story.description,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = suezOneRegular,
                    textAlign = TextAlign.Center
                ))
            Row(){
                Text(story.hour.toString(),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = suezOneRegular,
                    ))
            }

        }
    }
}