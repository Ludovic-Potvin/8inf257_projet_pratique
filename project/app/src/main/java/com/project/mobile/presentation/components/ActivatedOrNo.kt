import androidx.compose.ui.graphics.Color
import com.project.mobile.ui.theme.Purple
import com.project.mobile.ui.theme.WhitePurple

// Classe scellée pour représenter les états
open class ActivatedOrNo(
    val backgroundColor: Color,
    val activated: Boolean
)


// Objets pour les états activés et non activés
object Activated : ActivatedOrNo(
    backgroundColor = WhitePurple,  // Couleur personnalisée (par exemple, WhitePurple)
    activated = true
)

object NoActivated : ActivatedOrNo(
    backgroundColor = Purple,  // Couleur de non activation
    activated = false
)
