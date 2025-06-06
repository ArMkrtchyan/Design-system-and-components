package am.acba.compose.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

object DigitalTheme {

    val themedResources: ThemedResources
        @Composable
        @ReadOnlyComposable
        get() = LocalThemedResources.current

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
internal val LocalThemedResources = staticCompositionLocalOf<ThemedResources> { error("No Custom resources provided") }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigitalTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) darkColorScheme() else lightColorScheme()
    val resources = if (darkTheme) darkResourcesScheme() else lightResourcesScheme()
    val colorPalette = remember { colors }
    val resourcesPalette = remember { resources }
    val indication = object : Indication {}
    colorPalette.update(colors)
    resourcesPalette.update(resources)
    CompositionLocalProvider(
        LocalThemedResources provides resourcesPalette,
        LocalCustomColors provides colorPalette,
        LocalRippleConfiguration provides null,
        LocalIndication provides indication,
        content = content
    )
}