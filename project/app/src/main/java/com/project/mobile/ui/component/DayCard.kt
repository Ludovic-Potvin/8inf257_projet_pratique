package com.project.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme.colorScheme as theme
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
fun DayCard(label: String, isActive: Boolean, onClick: () -> Unit = {}) {
    Box(
        Modifier.size(32.dp)
        .background(
            color = if (isActive) theme.secondary else theme.primary,
            shape = CircleShape
        )
        .border(
            1.dp,
            theme.outline,
            CircleShape)
        .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(label,  style = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = trocchi)
        )
    }
}