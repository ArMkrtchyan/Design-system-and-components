package am.acba.composeComponents.expandableView

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.accordion.Accordion
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableViewScreen(title: String = "") {
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
                    .padding(bottom = 50.dp)
                    .padding(top = 20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                AccordionStateWithDivider()
                AccordionOutComeStateWithDivider()
                AccordionOutComeStateWithoutIconWithDivider()
                AccordionWithTitleStateWithDivider()
                AccordionWithTitleStateWithoutBackgroundWithDivider()
            }
        }
    }
}

@Composable
private fun AccordionStateWithDivider() {
    val expanded = remember { mutableStateOf(true) }
    PrimaryDivider(text = "Accordions with icon, title, amount and currency")
    VerticalSpacer(20)
    Accordion(
        avatarIcon = R.drawable.ic_income,
        avatarIconColor = DigitalTheme.colorScheme.contentBrand,
        title = "Փոխանցում հաշվին ",
        endText = "-5,200.00",
        endTextColor = DigitalTheme.colorScheme.contentBrand,
        currency = "AMD",
        expanded = expanded,
        onClick = { expanded.value = !expanded.value }
    ) {
        Column {
            Row {
                PrimaryText(
                    modifier = Modifier.weight(1f),
                    text = "Հաշվով ձևակերպման ամսաթիվ",
                    style = DigitalTheme.typography.smallRegular,
                    color = DigitalTheme.colorScheme.contentPrimaryTonal1
                )
                HorizontalSpacer(8)
                PrimaryText(
                    text = "15/05/2025",
                    style = DigitalTheme.typography.smallRegular,
                    color = DigitalTheme.colorScheme.contentPrimaryTonal1,
                    textAlign = TextAlign.End
                )
            }
            VerticalSpacer(8)
            PrimaryText(
                text = "Վճարում (SAS 10)",
                style = DigitalTheme.typography.smallRegular,
                color = DigitalTheme.colorScheme.contentPrimaryTonal1
            )
        }
    }
    VerticalSpacer(20)
}

@Composable
private fun AccordionOutComeStateWithDivider() {
    val expanded = remember { mutableStateOf(false) }
    PrimaryDivider(text = "Accordions with icon, title, amount and currency")
    VerticalSpacer(20)
    Accordion(
        avatarIcon = R.drawable.ic_outcome,
        avatarIconColor = DigitalTheme.colorScheme.contentDangerTonal1,
        title = "Փոխանցում հաշվին ",
        endText = "-5,200.00",
        currency = "AMD",
        expanded = expanded,
        onClick = { expanded.value = !expanded.value }
    ) {
        Column {
            Row {
                PrimaryText(
                    modifier = Modifier.weight(1f),
                    text = "Հաշվով ձևակերպման ամսաթիվ",
                    style = DigitalTheme.typography.smallRegular,
                    color = DigitalTheme.colorScheme.contentPrimaryTonal1
                )
                HorizontalSpacer(8)
                PrimaryText(
                    text = "15/05/2025",
                    style = DigitalTheme.typography.smallRegular,
                    color = DigitalTheme.colorScheme.contentPrimaryTonal1,
                    textAlign = TextAlign.End
                )
            }
            VerticalSpacer(8)
            PrimaryText(
                text = "Վճարում (SAS 10)",
                style = DigitalTheme.typography.smallRegular,
                color = DigitalTheme.colorScheme.contentPrimaryTonal1
            )
        }
    }
    VerticalSpacer(20)
}

@Composable
private fun AccordionOutComeStateWithoutIconWithDivider() {
    val expanded = remember { mutableStateOf(false) }
    PrimaryDivider(text = "Accordions with title, amount and currency")
    VerticalSpacer(20)
    Accordion(
        title = "Փոխանցում հաշվին ",
        endText = "-5,200.00",
        currency = "AMD",
        expanded = expanded,
        onClick = { expanded.value = !expanded.value }
    ) {
        Column {
            Row {
                PrimaryText(
                    modifier = Modifier.weight(1f),
                    text = "Հաշվով ձևակերպման ամսաթիվ",
                    style = DigitalTheme.typography.smallRegular,
                    color = DigitalTheme.colorScheme.contentPrimaryTonal1
                )
                HorizontalSpacer(8)
                PrimaryText(
                    text = "15/05/2025",
                    style = DigitalTheme.typography.smallRegular,
                    color = DigitalTheme.colorScheme.contentPrimaryTonal1,
                    textAlign = TextAlign.End
                )
            }
            VerticalSpacer(8)
            PrimaryText(
                text = "Վճարում (SAS 10)",
                style = DigitalTheme.typography.smallRegular,
                color = DigitalTheme.colorScheme.contentPrimaryTonal1
            )
        }
    }
    VerticalSpacer(20)
}

@Composable
private fun AccordionWithTitleStateWithDivider() {
    val expanded = remember { mutableStateOf(false) }
    PrimaryDivider(text = "Accordions with title")
    VerticalSpacer(20)
    Accordion(
        title = "Ինչպե՞ս բացել դասական ավանդ;",
        expanded = expanded,
        onClick = { expanded.value = !expanded.value }
    ) {
        PrimaryText(
            text = "If the tab is half-out of the view-port, when tapped on the tab it should get inside the view-port. The tab-switching behavior is similar to slide behavior, the content and the tab underline should slide from side to side.",
            style = DigitalTheme.typography.smallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1
        )
    }
    VerticalSpacer(20)
}

@Composable
private fun AccordionWithTitleStateWithoutBackgroundWithDivider() {
    val expanded = remember { mutableStateOf(false) }
    PrimaryDivider(text = "Accordions with title and divider")
    VerticalSpacer(20)
    Accordion(
        title = "Ինչպե՞ս բացել դասական ավանդ;",
        expanded = expanded,
        onClick = { expanded.value = !expanded.value },
        backgroundColor = Color.Transparent,
        showDivider = true
    ) {
        PrimaryText(
            text = "If the tab is half-out of the view-port, when tapped on the tab it should get inside the view-port. The tab-switching behavior is similar to slide behavior, the content and the tab underline should slide from side to side.",
            style = DigitalTheme.typography.smallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1
        )
    }
    VerticalSpacer(20)
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        ExpandableViewScreen()
    }
}