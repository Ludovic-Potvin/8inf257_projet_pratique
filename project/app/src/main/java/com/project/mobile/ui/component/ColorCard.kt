package com.project.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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

@Composable
fun ColorCard(color: Color, outline: Color, onClick: () -> Unit = {}) {
    Box(
        Modifier
            .size(32.dp)
            .background(color = color, shape = CircleShape)
            .border(1.dp, outline, CircleShape)
            .clickable(onClick = onClick)
    )
}