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
import com.project.mobile.viewmodel.RoutineVM
import suezOneRegular
import trocchi
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun RoutineCard(
    routine: RoutineVM,
    temperatures: List<Double?>,
    onClick: (RoutineVM) -> Unit,
    currentLanguage: String
) {
    val context = LocalContext.current
    val background = when (routine.priority) {
        PriorityType.LowPriority -> MaterialTheme.colorScheme.primary
        PriorityType.StandardPriority -> MaterialTheme.colorScheme.secondary
        PriorityType.HighPriority -> MaterialTheme.colorScheme.tertiary
    }

    val localizedContext = remember(currentLanguage) {
        val config = Configuration(context.resources.configuration)
        config.setLocale(Locale(currentLanguage))
        context.createConfigurationContext(config)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick(routine) }
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
                    CategoryBadge(routine.category)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = routine.title,
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        fontFamily = suezOneRegular,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = routine.description,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.White,
                        fontFamily = suezOneRegular,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                DayAndTimeRow(routine, temperatures)
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
private fun DayAndTimeRow(routine: RoutineVM, temperatures: List<Double?>) {
    val dayInitials = stringArrayResource(R.array.day_initials)
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()) }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        dayInitials.forEachIndexed { index, dayInitial ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                DayCard(
                    label = dayInitial,
                    isActive = routine.days.getOrElse(index) { false },
                    onClick = { /* Optional */ }
                )
                Spacer(modifier = Modifier.height(2.dp))
                val tempText = temperatures.getOrNull(index)?.toInt()?.toString()
                    Text(
                        text = tempText?.let { "$it°C" } ?: "--",
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.White,
                            fontFamily = trocchi
                        )
                    )

            }
            Spacer(modifier = Modifier.width(4.dp))
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = routine.getHourAsLocalTime().format(timeFormatter),
            style = TextStyle(
                fontSize = 20.sp,
                color = Color.White,
                fontFamily = suezOneRegular
            )
        )
    }
}
