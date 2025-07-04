package am.acba.compose.components.guide

import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@Composable
fun GuideBackgroundLayer(

) {

}

@Composable
@PreviewLightDark
fun GuideBackgroundLayerPreview(

) {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            GuideBackgroundLayer()
        }
    }
}