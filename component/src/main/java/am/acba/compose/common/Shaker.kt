package am.acba.compose.common

import am.acba.component.extensions.vibrate
import android.content.Context
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberShakeController(duration: Int = 500): Pair<Modifier, ShakeController> {
    val animatable = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val controller = remember { ShakeController(animatable, scope, duration, context) }

    val modifier = Modifier.offset { IntOffset(animatable.value.toInt(), 0) }
    return modifier to controller
}

class ShakeController(
    private val animatable: Animatable<Float, AnimationVector1D>,
    private val scope: CoroutineScope,
    private val duration: Int,
    private val context: Context
) {
    fun shake() {
        scope.launch {
            context.vibrate(50)
            animatable.stop()
            animatable.snapTo(0f)
            animatable.animateTo(
                targetValue = 0f,
                animationSpec = keyframes {
                    durationMillis = duration
                    0f at 0
                    12f at 50
                    -12f at 100
                    9f at 150
                    -9f at 200
                    6f at 250
                    -6f at 300
                    0f at 350
                }
            )
        }
    }
}