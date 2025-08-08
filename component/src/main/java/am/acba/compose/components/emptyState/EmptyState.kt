package am.acba.compose.components.emptyState

import am.acba.component.R
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.SecondaryButtonGreen
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieDynamicProperty
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    iconColor: Color? = DigitalTheme.colorScheme.contentPrimary,
    imageUrl: String? = null,
    lottieFileName: String? = null,
    lottieColor: Color? = null,
    lottieIterations: Int = 1,
    imageContainerWidth: Dp = 36.dp,
    imageContainerHeight: Dp = 36.dp,
    title: String? = null,
    description: String? = null,
    buttonText: String? = null,
    @DrawableRes buttonIcon: Int? = null,
    buttonIconGravity: Int = Gravity.START,
    onButtonClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(horizontal = 24.dp)
            .padding(bottom = 16.dp)
    ) {
        SetEmptyStateMedia(
            icon = icon,
            iconColor = iconColor,
            imageUrl = imageUrl,
            lottieFileName = lottieFileName,
            lottieColor = lottieColor,
            lottieIterations = lottieIterations,
            imageContainerWidth = imageContainerWidth,
            imageContainerHeight = imageContainerHeight
        )
        SetEmptyStateText(
            text = title,
            textColor = DigitalTheme.colorScheme.contentPrimary,
            style = DigitalTheme.typography.subTitle1Bold,
            textFieldId = "emptyStateTitle"
        )
        SetEmptyStateText(
            text = description,
            textColor = DigitalTheme.colorScheme.contentPrimaryTonal1,
            style = DigitalTheme.typography.body2Regular,
            textFieldId = "emptyStateDescription"
        )
        SetEmptyStateButton(
            buttonText = buttonText,
            buttonIcon = buttonIcon,
            buttonIconGravity = buttonIconGravity,
            onButtonClick = onButtonClick
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun SetEmptyStateMedia(
    icon: Int? = null,
    iconColor: Color? = DigitalTheme.colorScheme.contentPrimary,
    imageUrl: String? = null,
    imageContentScale: ContentScale = ContentScale.Crop,
    lottieFileName: String? = null,
    lottieColor: Color? = null,
    lottieColorKeyPaths: List<String> = listOf(),
    lottieIterations: Int = -1,
    imageContainerWidth: Dp = 36.dp,
    imageContainerHeight: Dp = 36.dp,
) {
    if (icon != null || imageUrl != null || lottieFileName != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .width(imageContainerWidth)
                    .height(imageContainerHeight)
            ) {
                when {
                    icon != null -> {
                        Image(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .id("emptyStateImage"),
                            painter = painterResource(id = icon),
                            colorFilter = iconColor?.let { ColorFilter.tint(it) },
                            contentDescription = null
                        )
                    }

                    imageUrl != null -> {
                        GlideImage(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .fillMaxSize()
                                .id("emptyStateImage"),
                            model = imageUrl,
                            contentDescription = null,
                            contentScale = imageContentScale
                        )
                    }

                    lottieFileName != null -> {
                        val preloaderLottieComposition by rememberLottieComposition(LottieCompositionSpec.Asset(lottieFileName))
                        val lottieColors: List<LottieDynamicProperty<*>> = if (lottieColorKeyPaths.isEmpty()) {
                            listOf(
                                rememberLottieDynamicProperty(
                                    property = LottieProperty.COLOR_FILTER,
                                    value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                                        lottieColor.hashCode(),
                                        BlendModeCompat.SRC_ATOP
                                    ),
                                    keyPath = arrayOf(
                                        "**"
                                    )
                                )
                            )
                        } else {
                            lottieColorKeyPaths.map {
                                rememberLottieDynamicProperty(
                                    property = LottieProperty.COLOR_FILTER,
                                    value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                                        lottieColor.hashCode(),
                                        BlendModeCompat.SRC_ATOP
                                    ),
                                    keyPath = arrayOf(
                                        it, "**"
                                    )
                                )
                            }
                        }
                        val dynamicProperties = rememberLottieDynamicProperties(*lottieColors.toTypedArray())
                        val progress by animateLottieCompositionAsState(
                            preloaderLottieComposition,
                            iterations = lottieIterations
                        )
                        LottieAnimation(
                            composition = preloaderLottieComposition,
                            modifier = Modifier
                                .fillMaxSize()
                                .id("emptyStateLottie"),
                            progress = { progress },
                            dynamicProperties = dynamicProperties
                        )
                    }
                }
            }
            VerticalSpacer(8.dp)
        }
    }
}

@Composable
private fun SetEmptyStateText(text: String? = null, textColor: Color, style: TextStyle, textFieldId: String) {
    text?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PrimaryText(text = it, color = textColor, style = style, textAlign = TextAlign.Center, modifier = Modifier.id(textFieldId))
            VerticalSpacer(8.dp)
        }
    }
}

@Composable
private fun SetEmptyStateButton(
    buttonText: String? = null,
    @DrawableRes buttonIcon: Int? = null,
    buttonIconGravity: Int = Gravity.START,
    onButtonClick: () -> Unit = {}
) {
    buttonText?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SecondaryButtonGreen(
                onClick = onButtonClick,
                text = it,
                icon = buttonIcon ?: -1,
                iconGravity = buttonIconGravity,
                isSmall = true,
                modifier = Modifier.id("emptyStateButton")
            )
            VerticalSpacer(8.dp)
        }
    }
}

@Composable
@PreviewLightDark
fun EmptyStatePreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            EmptyState(
                icon = R.drawable.ic_warning,
                title = "Բացեք խնայողական հաշիվ",
                description = "Բացեք խնայողական հաշիվ և խնայեք ձեր նպատակների համար",
                buttonText = "Խնայել",
                buttonIcon = R.drawable.ic_user_protect
            )
        }
    }
}