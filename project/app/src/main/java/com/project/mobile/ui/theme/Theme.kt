package com.project.mobile.ui.theme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = LightPurple,
    secondary = MediumPurple,
    tertiary = DarkPurple,
    background = BackgroundWhite,
    outline = WhitePurple
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkPurplePrimary,
    secondary = MediumPurpleSecondary,
    tertiary = LightPurpleTertiary,
    background = BackgroundDark,
    outline = OutlinePurple
)

private val NatureColorScheme = darkColorScheme(
    primary = LightGreen,
    secondary = MediumGreen,
    tertiary = DarkGreen,
    background = GreenBackgroundWhite,
    outline = WhiteGreen
)

private val EnergyColorScheme = darkColorScheme(
    primary = LightOrange,
    secondary = MediumOrange,
    tertiary = DarkOrange,
    background = OrangeBackgroundWhite,
    outline = WhiteOrange
)

private val FocusColorScheme = darkColorScheme(
    primary = LightBlue,
    secondary = MediumBlue,
    tertiary = DarkBlue,
    background = BlueBackgroundWhite,
    outline = WhiteBlue
)

@Composable
fun MobileprojectTheme(
    viewModel: ThemeViewModel,
    content: @Composable () -> Unit
) {
    val theme by viewModel.theme

    val colorScheme = when (theme) {
        0 -> LightColorScheme
        1 -> DarkColorScheme
        2 -> NatureColorScheme
        3 -> EnergyColorScheme
        4 -> FocusColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}