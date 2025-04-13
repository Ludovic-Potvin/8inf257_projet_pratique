
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.mobile.ui.theme.Purple

@Composable
fun ReminderHeader(
    onLanguageSelected: (String) -> Unit,
    currentLanguage: String
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        // LanguageSwitcher en haut à droite
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            LanguageSwitcher(
                onLanguageSelected = onLanguageSelected,
                currentLanguage = currentLanguage
            )
        }

        // Contenu principal centré
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "REMINDER",
                modifier = Modifier.padding(vertical = 10.dp),
                color = Purple,
                fontSize = 36.sp,
                fontFamily = suezOneRegular,
                textAlign = TextAlign.Center,
            )
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .background(Purple)
                    .width(260.dp)
            )
        }
    }
}