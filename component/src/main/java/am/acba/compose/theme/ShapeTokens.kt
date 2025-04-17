package am.acba.compose.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object ShapeTokens {
    val shapePrimaryButton: RoundedCornerShape = RoundedCornerShape(12.0.dp)
    val shapePrimaryButtonSmall: RoundedCornerShape = RoundedCornerShape(8.0.dp)
    val shapePrimaryInput: RoundedCornerShape = RoundedCornerShape(12.0.dp)
    val shapeCurrencyInput: RoundedCornerShape = RoundedCornerShape(topStart = 12.0.dp, bottomStart = 12.0.dp)
    val shapeCurrency: RoundedCornerShape = RoundedCornerShape(topEnd = 12.0.dp, bottomEnd = 12.0.dp)
}
