package am.acba.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

object DigitalTheme {

    val colorScheme: ColorTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalCustomColors.current

    /**
     * Retrieves the current [Typography] at the call site's position in the hierarchy.
     */
    val typography: TypographyTokens
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

internal val LocalCustomColors = staticCompositionLocalOf<ColorTokens> { error("No CustomColors provided") }
internal val LocalTypography = staticCompositionLocalOf { TypographyTokens() }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) darkColorScheme() else lightColorScheme()
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalCustomColors provides colorPalette, LocalRippleConfiguration provides null, content = content)
}