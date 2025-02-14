package com.project.mobile.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mobile.presentation.DayVM
import com.project.mobile.ui.theme.WhitePurple
import trocchi

@Composable
fun DayCard(day: DayVM) {

    Box(
        Modifier.size(32.dp)
        .background(color = day.state.backgroundColor,
            shape = CircleShape)
        .border(1.dp, WhitePurple, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(day.abreviation,  style = TextStyle(
            fontSize = 16.sp,
            color = Color.White,
            fontFamily = trocchi)
        )
    }

}