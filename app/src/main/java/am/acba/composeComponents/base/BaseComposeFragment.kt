package am.acba.composeComponents.base

import am.acba.components.mainScreen.MainActivity
import am.acba.utils.extensions.composableView
import am.acba.compose.theme.DigitalTheme
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.Fragment

abstract class BaseComposeFragment : Fragment() {
    private lateinit var focusManager: FocusManager
    protected var title: String = ""

    @Composable
    abstract fun SetContent()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        requireContext().composableView {
            title = arguments?.getString("Title") ?: ""
            DigitalTheme(MainActivity.darkTheme) {
                focusManager = LocalFocusManager.current
                val keyboardController = LocalSoftwareKeyboardController.current
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ) {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        },
                    color = DigitalTheme.colorScheme.backgroundBase
                ) {
                    SetContent()
                }
            }
        }


    fun clearFocus() {
        focusManager.clearFocus()
    }
}