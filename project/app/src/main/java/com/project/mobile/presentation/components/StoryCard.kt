package com.project.mobile.presentation.components

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.project.mobile.presentation.Priorite
import com.project.mobile.presentation.StoryVM
import com.project.mobile.ui.theme.LightPurple
import com.project.mobile.ui.theme.MediumPurple
import com.project.mobile.ui.theme.Purple
import com.project.mobile.ui.theme.WhitePurple
import suezOneRegular
import trocchi
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(UnstableApi::class)
@Composable
fun StoryCard(story: StoryVM, navController: NavController){
    val routineId = story.id
    val background = when (story.priorite) {
        Priorite.BASSE -> LightPurple
        Priorite.MOYENNE -> Purple
        Priorite.ELEVEE -> MediumPurple
    }
    Log.d("routineId", "routineId=$routineId")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                Log.d("Navigation", "Navigating to routine_modif/$routineId")
                navController.navigate("routine_modif/$routineId")
            }
            .background(background, shape = RoundedCornerShape(10.dp))
            .padding(12.dp, 12.dp),

    ){

        Column (modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

       /* Row(modifier = Modifier.fillMaxSize(),horizontalArrangement = Arrangement.SpaceBetween){

            Box(
            Modifier.size(30.dp)
                .align(Alignment.Start)
                .background(color = priorityColor,
                    shape = CircleShape
                )
                .border(1.dp, WhitePurple, CircleShape),
        )*/
            Box(modifier = Modifier
                .align(Alignment.End)
                .border(1.dp, WhitePurple, shape = RoundedCornerShape(10.dp)
                )
                .background(Purple, shape = RoundedCornerShape(10.dp))
                .padding(8.dp, 3.dp)
            ){
                Text(story.categorie.label,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = trocchi)
                )
            }
           /* }*/


            Text(story.title,
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    fontFamily = suezOneRegular,
                    textAlign = TextAlign.Center
                ))
            Text(story.description,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = suezOneRegular,
                    textAlign = TextAlign.Center
                ))
            Spacer(modifier = Modifier.height(5.dp))

                Row(horizontalArrangement = Arrangement.Center) {
                    story.days.forEach { (_, day) ->
                        DayCard(day)
                        Spacer(modifier = Modifier.width(5.dp))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    val formattedHour = try {
                        LocalTime.parse(story.hour, DateTimeFormatter.ofPattern("HH:mm"))
                            .format(DateTimeFormatter.ofPattern("HH:mm"))
                    } catch (e: Exception) {
                        Log.e("StoryCard", "Erreur de formatage de l'heure : ${story.hour}", e)
                        "Heure invalide"
                    }

                    Text(formattedHour, style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = suezOneRegular
                    ))



            }


        }
    }
}