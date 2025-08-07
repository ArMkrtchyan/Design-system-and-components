package am.acba.composeComponents.onboardingTooltip

import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.chips.PrimaryChip
import am.acba.compose.components.guide.ElementPositionAndSize
import am.acba.compose.components.guide.Guide
import am.acba.compose.components.guide.GuideItem
import am.acba.compose.components.listItem.ListItem
import am.acba.compose.theme.DigitalTheme
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideScreen(title: String = "") {
    val showGuide = remember { mutableStateOf(true) }
    val coordinates = remember { sortedMapOf<Int, ElementPositionAndSize>() }
    val coordinatesState = remember { mutableStateOf(sortedMapOf<Int, ElementPositionAndSize>()) }
    val scrollState = rememberScrollState()
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
                    .verticalScroll(scrollState),
            ) {
                Box(
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth()
                        .padding(bottom = 40.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    PrimaryChip(title = "Chip item", modifier = Modifier
                        .align(Alignment.TopStart)
                        .onGloballyPositioned { layoutCoordinates ->
                            coordinates[1] = ElementPositionAndSize(layoutCoordinates)
                            coordinatesState.value = coordinates
                        })
                    PrimaryChip(title = "Chip item", modifier = Modifier
                        .align(Alignment.TopEnd)
                        .onGloballyPositioned { layoutCoordinates ->
                            coordinates[2] = ElementPositionAndSize(layoutCoordinates)
                            coordinatesState.value = coordinates
                        })
                    PrimaryChip(title = "Chip item", modifier = Modifier
                        .align(Alignment.Center)
                        .onGloballyPositioned { layoutCoordinates ->
                            coordinates[3] = ElementPositionAndSize(layoutCoordinates)
                            coordinatesState.value = coordinates
                        }) {
                        showGuide.value = true
                    }
                    PrimaryChip(title = "Chip item", modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 100.dp, end = 100.dp)
                        .onGloballyPositioned { layoutCoordinates ->
                            coordinates[4] = ElementPositionAndSize(layoutCoordinates)
                            coordinatesState.value = coordinates
                        })
                    PrimaryChip(title = "Chip item", modifier = Modifier
                        .align(Alignment.BottomStart)
                        .onGloballyPositioned { layoutCoordinates ->
                            coordinates[5] = ElementPositionAndSize(layoutCoordinates)
                            coordinatesState.value = coordinates
                        })
                    PrimaryChip(title = "Chip item", modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .onGloballyPositioned { layoutCoordinates ->
                            coordinates[6] = ElementPositionAndSize(layoutCoordinates)
                            coordinatesState.value = coordinates
                        })

                }
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item 5555",
                    modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                        coordinates[7] = ElementPositionAndSize(layoutCoordinates)
                        coordinatesState.value = coordinates
                    }
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
                ListItem(
                    title = "List item"
                )
                VerticalSpacer(20.dp)
            }
        }
    }
    if (showGuide.value) {
        Guide(
            coordinatesState.value,
            coordinatesState.value.map {
                GuideItem(
                    title = "Ռեֆերալ կոդ",
                    description = "Ստացեք դրամական քեշբեք հավելվածի միջոցով կատարված վճարումների համար"
                )
            }, scrollState = scrollState
        ) {
            showGuide.value = false

        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        GuideScreen()
    }
}