package am.acba.compose.components.progressComponents

import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PrimarySegmentedProgressBar(
    modifier: Modifier = Modifier,
    segmentCount: Int,
    progress: Int,
    trackColor: Color = DigitalTheme.colorScheme.backgroundTonal3,
    progressColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    segmentSpacing: Dp = 4.dp,
    animationDuration: Int = 150
) {
    val animates = remember(segmentCount) {
        List(segmentCount) { Animatable(0f) }
    }

    LaunchedEffect(progress) {
        animates.forEachIndexed { index, anim ->
            if (index < progress) {
                launch {
                    anim.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(durationMillis = animationDuration)
                    )
                }
            } else {
                launch {
                    anim.animateTo(
                        targetValue = 0f,
                        animationSpec = tween(durationMillis = animationDuration)
                    )
                }
            }
        }
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(segmentSpacing)
    ) {
        animates.forEach { anim ->
            LinearProgressIndicator(
                progress = { anim.value },
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp),
                color = progressColor,
                trackColor = trackColor,
                drawStopIndicator = {}
            )
        }
    }
}
