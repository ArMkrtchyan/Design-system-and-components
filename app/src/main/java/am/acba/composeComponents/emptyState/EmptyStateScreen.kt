package am.acba.composeComponents.emptyState

import am.acba.component.R
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.components.emptyState.EmptyState
import am.acba.compose.theme.DigitalTheme
import android.view.Gravity
import androidx.annotation.DrawableRes
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmptyStateScreen(title: String = "") {
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
            PrimaryToolbar(title = title, actions = {
                IconButton(onClick = {

                }) {

                }
            })
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                SetEmptyItemWithDivider(
                    dividerTitle = "Full screen empty state with icon",
                    icon = R.drawable.ic_warning,
                    title = "Բացեք խնայողական հաշիվ",
                    description = "Բացեք խնայողական հաշիվ և խնայեք ձեր նպատակների համար",
                    buttonText = "Խնայել",
                    buttonIcon = R.drawable.ic_user_protect
                )
                SetEmptyItemWithDivider(
                    dividerTitle = "Full screen empty state with image",
                    icon = R.drawable.loan_illustration,
                    imageContainerWidth = 120.dp,
                    imageContainerHeight = 120.dp,
                    iconColor = null,
                    title = "Բացեք խնայողական հաշիվ",
                    description = "Բացեք խնայողական հաշիվ և խնայեք ձեր նպատակների համար",
                    buttonText = "Խնայել",
                    buttonIcon = R.drawable.ic_user_protect
                )
                SetEmptyItemWithDivider(
                    dividerTitle = "Empty state without button",
                    icon = R.drawable.ic_warning,
                    title = "Բացեք խնայողական հաշիվ",
                    description = "Բացեք խնայողական հաշիվ և խնայեք ձեր նպատակների համար",
                )
                SetEmptyItemWithDivider(
                    dividerTitle = "Empty state without media",
                    title = "Բացեք խնայողական հաշիվ",
                    description = "Բացեք խնայողական հաշիվ և խնայեք ձեր նպատակների համար",
                    buttonText = "Խնայել",
                    buttonIcon = R.drawable.ic_user_protect
                )
                SetEmptyItemWithDivider(
                    dividerTitle = "Empty state with icon and title",
                    icon = R.drawable.ic_warning,
                    title = "Բացեք խնայողական հաշիվ",
                )
                SetEmptyItemWithDivider(
                    dividerTitle = "Empty state with icon and description",
                    icon = R.drawable.ic_warning,
                    description = "Բացեք խնայողական հաշիվ և խնայեք ձեր նպատակների համար",
                )
                SetEmptyItemWithDivider(
                    dividerTitle = "Full screen empty state with lottie",
                    lottieFileName = "check_test.json",
                    title = "Բացեք խնայողական հաշիվ",
                    description = "Բացեք խնայողական հաշիվ և խնայեք ձեր նպատակների համար",
                    buttonText = "Խնայել",
                    buttonIcon = R.drawable.ic_user_protect
                )
                SetEmptyItemWithDivider(
                    dividerTitle = "Full screen empty state with image from url",
                    imageUrl = "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg",
                    imageContainerWidth = 80.dp,
                    imageContainerHeight = 80.dp,
                    title = "Բացեք խնայողական հաշիվ",
                    description = "Բացեք խնայողական հաշիվ և խնայեք ձեր նպատակների համար",
                    buttonText = "Խնայել",
                    buttonIcon = R.drawable.ic_user_protect
                )
            }
        }
    }
}

@Composable
private fun SetEmptyItemWithDivider(
    dividerTitle: String? = null,
    icon: Int? = null,
    iconColor: Color? = DigitalTheme.colorScheme.contentPrimary,
    imageUrl: String? = null,
    lottieFileName: String? = null,
    lottieColor: Color? = null,
    imageContainerWidth: Dp = 36.dp,
    imageContainerHeight: Dp = 36.dp,
    title: String? = null,
    description: String? = null,
    buttonText: String? = null,
    @DrawableRes buttonIcon: Int? = null,
    buttonIconGravity: Int = Gravity.START,
    onButtonClick: () -> Unit = {}
) {
    PrimaryDivider(text = dividerTitle)
    VerticalSpacer(20)
    EmptyState(
        icon = icon,
        iconColor = iconColor,
        imageUrl = imageUrl,
        lottieFileName = lottieFileName,
        lottieColor = lottieColor,
        imageContainerWidth = imageContainerWidth,
        imageContainerHeight = imageContainerHeight,
        title = title,
        description = description,
        buttonText = buttonText,
        buttonIcon = buttonIcon,
        buttonIconGravity = buttonIconGravity,
        onButtonClick = onButtonClick
    )
    VerticalSpacer(20)
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        EmptyStateScreen()
    }
}