package com.project.mobile.presentation.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import java.time.format.DateTimeFormatter

@Composable
fun StoryCard(story: StoryVM){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple, shape = RoundedCornerShape(10.dp))
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
            Spacer(modifier = Modifier.height(5.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){

                story.days.forEach { day ->
                        DayCard(day)
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(story.hour.format(DateTimeFormatter.ofPattern("HH:mm")), style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = suezOneRegular)
                    )
                }
            }

        }
    }
}