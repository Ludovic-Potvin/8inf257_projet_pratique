package com.project.mobile.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mobile.R
import com.project.mobile.common.CategoryType
import com.project.mobile.common.PriorityType
import com.project.mobile.viewmodel.StoryVM
import suezOneRegular
import trocchi
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun StoryCard(story: StoryVM, onClick: (StoryVM) -> Unit,currentLanguage: String) {
    val context = LocalContext.current
    val background = when (story.priority) {
        PriorityType.LowPriority -> MaterialTheme.colorScheme.primary
        PriorityType.StandardPriority -> MaterialTheme.colorScheme.secondary
        PriorityType.HighPriority -> MaterialTheme.colorScheme.tertiary
    }
    // Création des ressources localisées
    val localizedContext = remember(currentLanguage) {
        val config = Configuration(context.resources.configuration)
        config.setLocale(Locale(currentLanguage))
        context.createConfigurationContext(config)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick(story) }
            .background(background, shape = RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        CompositionLocalProvider(LocalContext provides localizedContext) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    CategoryBadge(story.category)
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Titre
                Text(
                    text = story.title,
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        fontFamily = suezOneRegular,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Description
                Text(
                    text = story.description,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = suezOneRegular,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Jours et heure
                DayAndTimeRow(story)
            }
        }
    }
}

@Composable
private fun CategoryBadge(category: CategoryType) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(10.dp)
            )
                    .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(10.dp))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = stringResource(id = category.labelResId),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = trocchi
                    )
                )
            }
}

@Composable
private fun DayAndTimeRow(story: StoryVM) {
    val context = LocalContext.current
    val dayInitials = stringArrayResource(R.array.day_initials)
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()) }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Jours de la semaine
        dayInitials.forEachIndexed { index, dayInitial ->
            DayCard(
                label = dayInitial,
                isActive = story.days.getOrElse(index) { false },
                onClick = { /* Gestion du clic si nécessaire */ }
            )
            Spacer(modifier = Modifier.width(4.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Heure
        Text(
            text = story.getHourAsLocalTime().format(timeFormatter),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = suezOneRegular
            )
        )
    }
}