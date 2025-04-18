
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReminderHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "REMINDER",
            modifier = Modifier.padding(vertical = 10.dp),
            color = colorScheme.tertiary,
            fontSize = 36.sp,
            fontFamily = suezOneRegular,
            textAlign = TextAlign.Center
        )
        Spacer(
            modifier = Modifier
                .height(1.dp)
                .background(colorScheme.tertiary)
                .width(260.dp)
        )
    }
}