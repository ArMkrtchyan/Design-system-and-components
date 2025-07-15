@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.bottomSheet

import am.acba.component.R
import am.acba.component.extensions.dpToPx
import am.acba.component.extensions.log
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun PrimaryBottomSheet(
    title: String = "",
    icon: Int? = R.drawable.ic_close,
    bottomSheetVisible: Boolean,
    dismiss: () -> Unit = {},
    modifier: Modifier = Modifier,
    properties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    contentHorizontalPadding: Dp = 16.dp,
    contentBottomPadding: Dp = 16.dp,
    calculatePercentForOpenFullScreen: Boolean = true,
    content: @Composable (sheetState: SheetState, coroutineScope: CoroutineScope) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { it == SheetValue.Expanded || it == SheetValue.Hidden }
    )
    val coroutineScope = rememberCoroutineScope()
    val openFullScreen = remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val fullScreenHeight = screenHeight - TopAppBarDefaults.windowInsets
        .only(WindowInsetsSides.Bottom)
        .asPaddingValues()
        .calculateBottomPadding()

    if (bottomSheetVisible) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            ModalBottomSheet(
                onDismissRequest = {
                    dismiss.invoke()
                },
                modifier = if (openFullScreen.value) modifier
                    .height(fullScreenHeight)
                    .align(Alignment.BottomCenter)
                else modifier,
                sheetState = sheetState,
                dragHandle = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Spacer(
                            modifier = Modifier
                                .width(32.dp)
                                .height(4.dp)
                                .background(DigitalTheme.colorScheme.contentPrimaryTonal2, ShapeTokens.shapeRound)
                                .align(Alignment.Center)
                        )
                    }
                },
                properties = properties,
                shape = ShapeTokens.shapeBottomSheet,
                containerColor = DigitalTheme.colorScheme.backgroundTonal1
            ) {
                Column(
                    modifier = Modifier.onGloballyPositioned {
                        val contentHeightDouble = it.size.height * 1.0
                        val displayHeightDouble = screenHeight.value.dpToPx() * 1.0
                        val dimensionInPercent =
                            contentHeightDouble.log("HeightTag", "contentHeightDouble -> ").div(displayHeightDouble.log("HeightTag", "displayHeightDouble -> ")) * 100.0
                        openFullScreen.value = if (calculatePercentForOpenFullScreen) dimensionInPercent.log("HeightTag", "dimensionInPercent -> ") > 70 else false
                    }
                ) {
                    if (title.isNotEmpty() || icon != null) {
                        BottomSheetHeader(title, icon) {
                            coroutineScope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion { dismiss.invoke() }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = contentHorizontalPadding)
                            .padding(bottom = contentBottomPadding)
                    ) {
                        content.invoke(sheetState, coroutineScope)
                    }
                }

            }
        }
    }
}

@Composable
private fun BottomSheetHeader(title: String = "", icon: Int? = null, onDismissRequest: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
    ) {
        PrimaryText(text = title, modifier = Modifier.weight(1f), style = DigitalTheme.typography.body1Bold)
        if (icon != null) {
            PrimaryIcon(painter = painterResource(icon), modifier = Modifier.clickable { onDismissRequest.invoke() })
        }
    }
}

fun closeBottomSheet(scope: CoroutineScope, state: SheetState, onComplete: () -> Unit) {
    scope.launch { state.hide() }.invokeOnCompletion {
        if (!state.isVisible) {
            onComplete.invoke()
        }
    }
}

@Composable
@PreviewLightDark
fun PrimaryBottomSheetPreview() {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            BottomSheetHeader("Ընտրել հաշվեհամարը", R.drawable.ic_close) {}
        }
    }
}