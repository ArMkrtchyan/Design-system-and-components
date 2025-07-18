package am.acba.compose.components.progressComponents

import am.acba.compose.theme.DigitalTheme
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ComponentSegmentedProgressBar(
    modifier: Modifier = Modifier,
    segmentCount: Int,
    progress: Int,
    trackColor: Color = DigitalTheme.colorScheme.backgroundTonal3,
    progressColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    segmentSpacing: Dp = 4.dp,
    animationDuration: Int = 100
) {
    val animates = remember(segmentCount) {
        List(segmentCount) { Animatable(0f) }
    }

    LaunchedEffect(progress) {
        animates.forEachIndexed { index, anim ->
            val targetValue = if (index < progress) 1f else 0f
            launch {
                anim.animateTo(
                    targetValue = targetValue,
                    animationSpec = tween(durationMillis = animationDuration)
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
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
