package am.acba.composeComponents.chips

import am.acba.component.R
import am.acba.compose.HorizontalSpacer
import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.chips.ChipSizeEnum
import am.acba.compose.components.chips.ChipStateEnum
import am.acba.compose.components.chips.PrimaryChip
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.theme.DigitalTheme
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipsScreen(title: String = "") {
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
                PrimaryDivider(text = "Chips size S with text")
                VerticalSpacer(16)
                ChipsSmallWithText()
                VerticalSpacer(20)
                PrimaryDivider(text = "Chips size L with text")
                VerticalSpacer(16)
                ChipsLargeWithText()
                VerticalSpacer(20)
                PrimaryDivider(text = "Chips size S with text and end icon")
                VerticalSpacer(16)
                ChipsSmallWithTextAndEndIcon()
                VerticalSpacer(20)
                PrimaryDivider(text = "Chips size L with text and end icon")
                VerticalSpacer(16)
                ChipsLargeWithTextAndEndIcon()
                VerticalSpacer(20)
                PrimaryDivider(text = "Chips size S with text,avatar and end icon")
                VerticalSpacer(16)
                ChipsSmallWithTextAvatarAndEndIcon()
                VerticalSpacer(20)
                PrimaryDivider(text = "Chips size L with text,avatar and end icon")
                VerticalSpacer(16)
                ChipsLargeWithTextAvatarAndEndIcon()
                VerticalSpacer(20)
                PrimaryDivider(text = "Chips size S with text and two icons")
                VerticalSpacer(16)
                ChipsSmallWithTextAndTwoIcons()
                VerticalSpacer(20)
                PrimaryDivider(text = "Chips size L with text and two icons")
                VerticalSpacer(16)
                ChipsLargeWithTextAndTwoIcons()
                VerticalSpacer(20)

            }
        }
    }
}

@Composable
private fun ChipsSmallWithText() {
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        PrimaryChip(
            title = "Chip component"
        )
        HorizontalSpacer(20)
        PrimaryChip(
            title = "Chip component",
            chipStateEnum = ChipStateEnum.SELECTED
        )
    }
}

@Composable
private fun ChipsLargeWithText() {
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        PrimaryChip(
            chipSizeEnum = ChipSizeEnum.LARGE,
            title = "Chip component"
        )
        HorizontalSpacer(20)
        PrimaryChip(
            chipSizeEnum = ChipSizeEnum.LARGE,
            title = "Chip component",
            chipStateEnum = ChipStateEnum.SELECTED
        )
    }
}

@Composable
private fun ChipsSmallWithTextAndEndIcon() {
    val context = LocalContext.current
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        PrimaryChip(
            title = "Chip component",
            endIcon = R.drawable.ic_close,
            onEndIconClick = {
                Toast.makeText(context, "End icon clicked", Toast.LENGTH_SHORT).show()
            },
            onClick = {
                Toast.makeText(context, "Chip clicked", Toast.LENGTH_SHORT).show()
            }
        )
        HorizontalSpacer(20)
        PrimaryChip(
            title = "Chip component",
            chipStateEnum = ChipStateEnum.SELECTED,
            endIcon = R.drawable.ic_close,
            onEndIconClick = {
                Toast.makeText(context, "End icon clicked", Toast.LENGTH_SHORT).show()
            },
            onClick = {
                Toast.makeText(context, "Chip clicked", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
private fun ChipsLargeWithTextAndEndIcon() {
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        PrimaryChip(
            chipSizeEnum = ChipSizeEnum.LARGE,
            title = "Chip component",
            endIcon = R.drawable.ic_down,
        )
        HorizontalSpacer(20)
        PrimaryChip(
            chipSizeEnum = ChipSizeEnum.LARGE,
            title = "Chip component",
            chipStateEnum = ChipStateEnum.SELECTED,
            endIcon = R.drawable.ic_down,
        )
    }
}

@Composable
private fun ChipsSmallWithTextAvatarAndEndIcon() {
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        PrimaryChip(
            title = "Chip component",
            endIcon = R.drawable.ic_close,
            imageRes = R.drawable.default_avatar,
            badgeType = BadgeEnum.DOT
        )
        HorizontalSpacer(20)
        PrimaryChip(
            title = "Chip component",
            chipStateEnum = ChipStateEnum.SELECTED,
            endIcon = R.drawable.ic_close,
            imageRes = R.drawable.default_avatar,
            badgeType = BadgeEnum.DOT
        )
    }
}

@Composable
private fun ChipsLargeWithTextAvatarAndEndIcon() {
    val context = LocalContext.current
    LazyRow {
        items(arrayListOf(ChipStateEnum.NOT_SELECTED, ChipStateEnum.SELECTED)) {
            PrimaryChip(
                chipSizeEnum = ChipSizeEnum.LARGE,
                title = "Chip component",
                chipStateEnum = it,
                endIcon = R.drawable.ic_down,
                imageRes = R.drawable.default_avatar,
                badgeType = BadgeEnum.DOT,
                onEndIconClick = {
                    Toast.makeText(context, "End icon clicked", Toast.LENGTH_SHORT).show()
                },
                onClick = {
                    Toast.makeText(context, "Chip clicked", Toast.LENGTH_SHORT).show()
                }
            )
            HorizontalSpacer(20)

        }
    }
}

@Composable
private fun ChipsSmallWithTextAndTwoIcons() {
    Row(Modifier.horizontalScroll(rememberScrollState())) {
        PrimaryChip(
            title = "Chip component",
            endIcon = R.drawable.ic_close,
            icon = R.drawable.ic_house
        )
        HorizontalSpacer(20)
        PrimaryChip(
            title = "Chip component",
            chipStateEnum = ChipStateEnum.SELECTED,
            endIcon = R.drawable.ic_close,
            icon = R.drawable.ic_house
        )
    }
}

@Composable
private fun ChipsLargeWithTextAndTwoIcons() {
    LazyRow {
        items(arrayListOf(ChipStateEnum.NOT_SELECTED, ChipStateEnum.SELECTED)) {
            PrimaryChip(
                chipSizeEnum = ChipSizeEnum.LARGE,
                title = "Chip component",
                chipStateEnum = it,
                endIcon = R.drawable.ic_down,
                icon = R.drawable.ic_house
            )
            HorizontalSpacer(20)
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        ChipsScreen()
    }
}