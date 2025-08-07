package am.acba.composeComponents.expandableCard

import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.components.expandableCard.AmountRow
import am.acba.compose.components.expandableCard.ExpandableCard
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCardScreen(title: String = "") {
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
                    .padding(bottom = 50.dp)
                    .padding(top = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                val state = remember { mutableStateOf(false) }
                ExpandableCard(
                    expanded = state.value,
                    onCardClick = { state.value = !state.value },
                    pinedContent = {
                        PrimaryText(
                            text = "Ընթացիկ պարտք",
                            style = DigitalTheme.typography.xSmallRegular,
                            color = DigitalTheme.colorScheme.contentPrimaryTonal1
                        )
                        VerticalSpacer(4.dp)
                        PrimaryText(
                            text = "400,000.00 AMD",
                            style = DigitalTheme.typography.heading6Bold,
                        )
                    },
                    animatedContent = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            VerticalSpacer(24.dp)
                            PrimaryDivider()
                            VerticalSpacer(12.dp)
                            AmountRow(
                                title = "Սկզբնական գումար",
                                value = "400,000.00 AMD"
                            )
                            AmountRow(
                                title = "Պայմանագրի համար",
                                value = "400,000.00 AMD"
                            )
                        }
                    },
                )
            }
        }
    }
}


@Composable
@PreviewLightDark
fun ExpandableCardScreenPreview() {
    DigitalTheme {
        ExpandableCardScreen()
    }
}