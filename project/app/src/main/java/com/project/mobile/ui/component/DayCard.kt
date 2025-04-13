package com.project.mobile.ui.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme as theme
import androidx.compose.foundation.Image
import coil.compose.rememberImagePainter
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import trocchi

@Composable
fun DayCard(label: String, isActive: Boolean, weatherIcon: String, onClick: () -> Unit = {}) {
    Log.d("WeatherIcon", "Icon URL: http://openweathermap.org/img/wn/$weatherIcon@2x.png")
    Box(
        modifier = Modifier
            .size(50.dp) // Taille ajustée pour inclure l'icône
            .background(
                color = if (isActive) theme.secondary else theme.primary,
                shape = CircleShape
            )
            .border(
                1.dp,
                theme.outline,
                CircleShape
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Affichage de l'icône météo
            Image(
                painter = rememberImagePainter("http://openweathermap.org/img/wn/$weatherIcon@2x.png"),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(20.dp) // Taille de l'icône
            )
            // Affichage du jour
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = trocchi
                )
            )
        }
    }
}
