package am.acba.composeComponents.base

import am.acba.components.mainScreen.MainActivity
import am.acba.compose.theme.DigitalTheme
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.fragment.app.Fragment

abstract class BaseComposeFragment : Fragment() {
    private lateinit var focusManager: FocusManager

    @Composable
    abstract fun SetContent()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = ComposeView(inflater.context).apply {
        setContent {
            DigitalTheme(MainActivity.darkTheme) {
                SetContent()
                focusManager = LocalFocusManager.current
            }
        }
    }
}