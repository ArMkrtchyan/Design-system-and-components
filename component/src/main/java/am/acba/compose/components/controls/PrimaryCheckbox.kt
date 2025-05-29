package am.acba.compose.components.controls

import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable

@Composable
fun PrimaryCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    enabled: Boolean = true,
    text: String? = null,
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        enabled = enabled
    )
}