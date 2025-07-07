package am.acba.composeComponents.onboardingTooltip

import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.chips.PrimaryChip
import am.acba.compose.components.guide.Guide
import am.acba.compose.components.guide.GuideItem
import am.acba.compose.theme.DigitalTheme
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionOnScreen
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuideScreen(title: String = "") {
    val showGuide = remember { mutableStateOf(true) }
    val coordinates = remember { sortedMapOf<Int, Triple<Float, Float, IntSize>>() }
    val coordinatesState = remember { mutableStateOf(sortedMapOf<Int, Triple<Float, Float, IntSize>>()) }
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 40.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                PrimaryChip(title = "Chip item", modifier = Modifier
                    .align(Alignment.TopStart)
                    .onGloballyPositioned {
                        coordinates[1] = Triple(it.positionOnScreen().x, it.positionOnScreen().y, it.size)
                        coordinatesState.value = coordinates
                    })
                PrimaryChip(title = "Chip item", modifier = Modifier
                    .align(Alignment.TopEnd)
                    .onGloballyPositioned {
                        coordinates[2] = Triple(it.positionOnScreen().x, it.positionOnScreen().y, it.size)
                        coordinatesState.value = coordinates
                    })
                PrimaryChip(title = "Chip item", modifier = Modifier
                    .align(Alignment.Center)
                    .onGloballyPositioned {
                        coordinates[3] = Triple(it.positionOnScreen().x, it.positionOnScreen().y, it.size)
                        coordinatesState.value = coordinates
                    }) { showGuide.value = true }
                PrimaryChip(title = "Chip item", modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 100.dp, end = 100.dp)
                    .onGloballyPositioned {
                        coordinates[4] = Triple(it.positionOnScreen().x, it.positionOnScreen().y, it.size)
                        coordinatesState.value = coordinates
                    })
                PrimaryChip(title = "Chip item", modifier = Modifier
                    .align(Alignment.BottomStart)
                    .onGloballyPositioned {
                        coordinates[5] = Triple(it.positionOnScreen().x, it.positionOnScreen().y, it.size)
                        coordinatesState.value = coordinates
                    })
                PrimaryChip(title = "Chip item", modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .onGloballyPositioned {
                        coordinates[6] = Triple(it.positionOnScreen().x, it.positionOnScreen().y, it.size)
                        coordinatesState.value = coordinates
                    })

            }
        }
    }
    if (showGuide.value) {
        Guide(
            coordinatesState,
            coordinatesState.value.map {
                GuideItem(
                    title = "Ռեֆերալ կոդ",
                    description = "Ստացեք դրամական քեշբեք հավելվածի միջոցով կատարված վճարումների համար"
                )
            }) {
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