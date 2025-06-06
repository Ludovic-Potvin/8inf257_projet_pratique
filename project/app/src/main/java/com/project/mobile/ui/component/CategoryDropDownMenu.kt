package com.project.mobile.ui.component

import android.content.res.Configuration
import com.project.mobile.R
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
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mobile.common.CategoryType
import java.util.Locale

@Composable
fun CategoryDropdownMenu(
    selectedCategory: CategoryType,
    onCategorySelected: (CategoryType) -> Unit,
    currentLanguage: String
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Solution clé : Utilisation directe des ressources avec la locale forcée
    val resources = remember(currentLanguage) {
        val config = Configuration(context.resources.configuration)
        config.setLocale(Locale(currentLanguage))
        val localizedContext = context.createConfigurationContext(config)
        localizedContext.resources
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.White, shape = RoundedCornerShape(5.dp))
            .clickable { expanded = true }
            .background(colorScheme.tertiary, shape = RoundedCornerShape(5.dp))
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
                text = resources.getString(selectedCategory.labelResId), // <-- Ici
                color = Color.White,
                fontSize = 16.sp
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = stringResource(R.string.category_dropdown_desc),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(colorScheme.tertiary)
        ) {
            CategoryType.entries.forEach { category ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = resources.getString(category.labelResId), // <-- Ici
                            color = Color.White,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    onClick = {
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}