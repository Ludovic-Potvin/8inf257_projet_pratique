package com.project.mobile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mobile.common.PriorityType
import com.project.mobile.ui.theme.Purple

@Composable
fun PriorityDropdownMenu(selectedPriority: PriorityType, onPrioritySelected: (PriorityType) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val priorities = PriorityType.entries

    // Todo find a way to directly use the enum, this is not scalable
    val priorityLabels = mapOf(
        PriorityType.HighPriority to "High Priority",
        PriorityType.StandardPriority to "Standard Priority",
        PriorityType.LowPriority to "Low Priority"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, shape = RoundedCornerShape(5.dp))
            .clickable { expanded = true }
            .background(Purple, shape = RoundedCornerShape(5.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = priorityLabels[selectedPriority] ?: "Unknown",
                color = Color.White,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Purple)
        ) {
            priorities.forEach { priority ->
                DropdownMenuItem(
                    { Text(priorityLabels[priority] ?: "Unknown", color = Color.White) },
                    onClick = {
                        onPrioritySelected(priority)
                        expanded = false
                    }
                )
            }
        }
    }
}