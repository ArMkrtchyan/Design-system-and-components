package am.acba.composeComponents.snackbar

import am.acba.composeComponents.base.BaseComposeFragment
import androidx.compose.runtime.Composable


class SnackBarComposeFragment : BaseComposeFragment() {
    @Composable
    override fun SetContent() {
        SnackbarScreen(title)
    }
}