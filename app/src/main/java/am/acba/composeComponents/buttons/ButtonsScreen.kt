package am.acba.composeComponents.buttons

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.EmojiButton
import am.acba.compose.components.GhostButton
import am.acba.compose.components.PrimaryButton
import am.acba.compose.components.PrimaryButtonSmall
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.SecondaryButtonGreen
import am.acba.compose.components.SecondaryButtonGrey
import am.acba.compose.theme.DigitalTheme
import android.view.Gravity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonsScreen(title: String = "") {
    Box(
        modifier = Modifier
            .background(DigitalTheme.colorScheme.backgroundBase)
            .fillMaxSize()
            .padding(
                bottom = TopAppBarDefaults.windowInsets
                    .only(WindowInsetsSides.Bottom)
                    .asPaddingValues()
                    .calculateBottomPadding()
            )
    ) {
        Column(Modifier.fillMaxSize()) {
            PrimaryToolbar(title = title)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                VerticalSpacer(16)
                EmojiButton(
                    onClick = {

                    },
                    text = "Emoji button",
                    iconGravity = Gravity.END,
                    emojiIcon = am.acba.component.R.drawable.ic_add_small,
                )
                VerticalSpacer(16)
                PrimaryButton(
                    onClick = {

                    },
                    text = "Primary button",
                    icon = am.acba.component.R.drawable.ic_add_small,
                    iconGravity = Gravity.START,
                )
                VerticalSpacer(16)
                PrimaryButtonSmall(
                    onClick = {

                    },
                    text = "Primary button",
                    icon = am.acba.component.R.drawable.ic_add_small,
                    iconGravity = Gravity.START,
                )
                VerticalSpacer(16)
                PrimaryButton(
                    onClick = {

                    },
                    text = "Primary button disabled",
                    icon = am.acba.component.R.drawable.ic_car_accident,
                    iconGravity = Gravity.END,
                    enabled = false
                )
                VerticalSpacer(16)
                SecondaryButtonGreen(
                    onClick = {

                    },
                    text = "Secondary button",
                    icon = am.acba.component.R.drawable.ic_add_small,
                    iconGravity = Gravity.START,
                )
                VerticalSpacer(16)
                SecondaryButtonGreen(
                    onClick = {

                    },
                    text = "Secondary button disabled",
                    icon = am.acba.component.R.drawable.ic_car_accident,
                    iconGravity = Gravity.END,
                    enabled = false
                )
                VerticalSpacer(16)
                SecondaryButtonGrey(
                    onClick = {

                    },
                    text = "Secondary button grey",
                    icon = am.acba.component.R.drawable.ic_add_small,
                    iconGravity = Gravity.START,
                )
                VerticalSpacer(16)
                SecondaryButtonGrey(
                    onClick = {

                    },
                    text = "Secondary button grey disabled",
                    icon = am.acba.component.R.drawable.ic_car_accident,
                    iconGravity = Gravity.END,
                    enabled = false
                )
                VerticalSpacer(16)
                GhostButton(
                    onClick = {

                    },
                    text = "Ghost button",
                    icon = am.acba.component.R.drawable.ic_add_small,
                    iconGravity = Gravity.START,
                )
                VerticalSpacer(16)
                GhostButton(
                    onClick = {

                    },
                    text = "Ghost button disabled",
                    icon = am.acba.component.R.drawable.ic_car_accident,
                    iconGravity = Gravity.END,
                    enabled = false
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun ButtonsScreenPreview() {
    DigitalTheme {
        ButtonsScreen()
    }
}