package com.project.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.project.mobile.common.PriorityType
import com.project.mobile.viewmodel.StoryVM
import androidx.compose.material3.MaterialTheme.colorScheme as theme
import suezOneRegular
import trocchi

@Composable
fun StoryCard(story: StoryVM, onClick: (StoryVM) -> Unit) {

    val background = when (story.priority) {
        PriorityType.LowPriority -> theme.primary
        PriorityType.StandardPriority -> theme.secondary
        PriorityType.HighPriority -> theme.tertiary
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick(story) }
            .background(background, shape = RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Affichage de la catégorie
            Box(
                modifier = Modifier
                    .align(Alignment.End)
                    .border(1.dp, theme.outline, shape = RoundedCornerShape(10.dp))
                    .background(theme.secondary, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp, 3.dp)
            ) {
                Text(
                    story.category.label,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = trocchi
                    )
                )
            }

            // Affichage du titre de l'histoire
            Text(
                story.title,
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    fontFamily = suezOneRegular,
                    textAlign = TextAlign.Center
                )
            )

            // Affichage de la description
            Text(
                story.description,
                style = TextStyle(
                    fontSize = 18.sp,
                    color = Color.White,
                    fontFamily = suezOneRegular,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(5.dp))

            // Affichage des jours et icônes météo
            Row(horizontalArrangement = Arrangement.Center) {
                story.days.forEachIndexed { index, day ->
                    // Nous n'affichons plus ici l'icône météo car elle est récupérée dynamiquement
                    DayCard(
                        label = "SMTWTFS"[index].toString(),
                        isActive = day,
                        weatherIcon = "", // Pas besoin d'envoyer ici l'icône météo, elle sera récupérée au moment de l'affichage
                        onClick = { onClick(story) }
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                val formattedHour = story.getHourAsLocalTime().toString()

                Text(
                    formattedHour,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.White,
                        fontFamily = suezOneRegular
                    )
                )
            }
        }
    }
}
