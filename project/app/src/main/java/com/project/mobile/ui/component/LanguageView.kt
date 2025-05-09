import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.wrapContentSize
    import androidx.compose.material3.DropdownMenu
    import androidx.compose.material3.DropdownMenuItem
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextButton
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.Composable
import com.project.mobile.language.LanguageViewModel
import androidx.compose.material3.MaterialTheme.colorScheme as theme

@Composable
fun LanguageSwitcher(
    onLanguageSelected: (String) -> Unit,
    currentLanguage: String
) {
    var expanded by remember { mutableStateOf(false) }
    val languages = mapOf(
        "en" to "English",
        "fr" to "Français",
        "es" to "Español",
        "pt" to "Português"
    )

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        TextButton(onClick = { expanded = true }) {
            Text(
                text = languages[currentLanguage] ?: "English",
                color = theme.primary,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { (code, label) ->
                DropdownMenuItem(
                    text = { Text(label, color = theme.primary) },
                    onClick = {
                        onLanguageSelected(code)
                        expanded = false
                    }
                )
            }
        }
    }
}