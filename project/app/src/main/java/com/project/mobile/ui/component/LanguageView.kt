import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import com.project.mobile.language.LanguageViewModel
import androidx.compose.material3.MaterialTheme.colorScheme as theme

@Composable
fun LanguageSwitcher(
    onLanguageSelected: (String) -> Unit,
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    val currentLanguage = languageViewModel.currentLanguage

    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.width(150.dp)
        ) {
            Text(
                text = when(currentLanguage) {
                    "fr" -> "Français"
                    "es" -> "Español"
                    "pt" -> "Português"
                    else -> "English"
                },
                color = theme.primary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    onLanguageSelected("en")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Français") },
                onClick = {
                    onLanguageSelected("fr")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Español") },
                onClick = {
                    onLanguageSelected("es")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Português") },
                onClick = {
                    onLanguageSelected("pt")
                    expanded = false
                }
            )
        }
    }
}