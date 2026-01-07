package am.acba.compose.components.tableComponent

import am.acba.component.R
import am.acba.component.viewUtil.ViewUtil.copyWithVibration
import am.acba.compose.common.HorizontalSpacer
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryIcon
import am.acba.compose.components.PrimaryText
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.components.listItem.ListItem
import am.acba.compose.components.listItem.ListItemStartAvatarSizeEnum
import am.acba.compose.theme.DigitalTheme
import am.acba.compose.theme.ShapeTokens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TableComponent(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleMaxLines: Int? = null,
    titleColor: Color? = null,
    titleStyle: TextStyle? = null,
    description: String = "",
    descriptionMaxLines: Int? = null,
    descriptionColor: Color? = null,
    descriptionStyle: TextStyle? = null,
    avatarBackgroundModifier: Modifier = Modifier,
    avatarBadgeModifier: Modifier = Modifier,
    avatarContentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    listItemStartAvatarSizeEnum: ListItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON_WITH_BACKGROUND,
    avatarBackgroundColor: Color = DigitalTheme.colorScheme.backgroundTonal1,
    avatarBackgroundRadius: Int = 8,
    avatarIcon: Int? = null,
    avatarIconColor: Color? = DigitalTheme.colorScheme.contentBrand,
    avatarIconPadding: Dp = 8.dp,
    endIcon: Int? = null,
    endIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    endIconPadding: Int = 0,
    endIconBackgroundColor: Color = Color.Transparent,
    endIconBackgroundRadius: Int = 4,
    avatarImageUrl: String? = null,
    avatarClipPercent: Int = 0,
    avatarImageCornerRadius: Int? = null,
    avatarContentScale: ContentScale = ContentScale.Crop,
    avatarText: String? = null,
    avatarTextColor: Color = DigitalTheme.colorScheme.contentSecondary,
    avatarBadgeType: BadgeEnum = BadgeEnum.NONE,
    avatarBadgeIcon: Int? = null,
    avatarBadgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    avatarBadgeIconColor: Color = DigitalTheme.colorScheme.contentSecondary,
    avatarBadgeBorderColor: Color = DigitalTheme.colorScheme.borderSecondary,
    tableItems: List<Pair<String, String>> = emptyList(),
    minimumVisibleItemsCount: Int = 7,
    borderColor: Color = DigitalTheme.colorScheme.borderNeutral
) {
    val expanded = rememberSaveable { mutableStateOf(false) }
    val expandable = tableItems.size > minimumVisibleItemsCount
    val arrowRotation by animateFloatAsState(
        targetValue = if (expanded.value) 180f else 0f,
        label = "accordion-arrow",
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, ShapeTokens.shapePrimaryButton)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        if (!title.isNullOrEmpty()) {
            TableHeader(
                title = title,
                titleMaxLines = titleMaxLines,
                titleColor = titleColor,
                titleStyle = titleStyle,
                description = description,
                descriptionMaxLines = descriptionMaxLines,
                descriptionColor = descriptionColor,
                descriptionStyle = descriptionStyle,
                avatarBackgroundModifier = avatarBackgroundModifier,
                avatarBadgeModifier = avatarBadgeModifier,
                avatarContentModifier = avatarContentModifier,
                avatarType = avatarType,
                listItemStartAvatarSizeEnum = listItemStartAvatarSizeEnum,
                avatarBackgroundColor = avatarBackgroundColor,
                avatarBackgroundRadius = avatarBackgroundRadius,
                avatarIcon = avatarIcon,
                avatarIconColor = avatarIconColor,
                avatarIconPadding = avatarIconPadding,
                endIcon = endIcon,
                endIconColor = endIconColor,
                endIconPadding = endIconPadding,
                endIconBackgroundColor = endIconBackgroundColor,
                endIconBackgroundRadius = endIconBackgroundRadius,
                avatarImageUrl = avatarImageUrl,
                avatarClipPercent = avatarClipPercent,
                avatarImageCornerRadius = avatarImageCornerRadius,
                avatarContentScale = avatarContentScale,
                avatarText = avatarText,
                avatarTextColor = avatarTextColor,
                avatarBadgeType = avatarBadgeType,
                avatarBadgeIcon = avatarBadgeIcon,
                avatarBadgeBackgroundColor = avatarBadgeBackgroundColor,
                avatarBadgeIconColor = avatarBadgeIconColor,
                avatarBadgeBorderColor = avatarBadgeBorderColor,
            )
        } else {
            VerticalSpacer(8.dp)
        }
        MainContent(tableItems.take(minimumVisibleItemsCount))
        if (expandable) {
            AnimatedContent(expanded, tableItems, minimumVisibleItemsCount)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
                    .clickable {
                        expanded.value = !expanded.value
                    }
            ) {
                PrimaryIcon(
                    painter = painterResource(R.drawable.ic_down_2), modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.Center)
                        .graphicsLayer(rotationX = arrowRotation)
                )
            }
        } else {
            VerticalSpacer(8.dp)
        }
    }
}

@Composable
private fun TableHeader(
    title: String,
    titleMaxLines: Int? = null,
    titleColor: Color? = null,
    titleStyle: TextStyle? = null,
    description: String = "",
    descriptionMaxLines: Int? = null,
    descriptionColor: Color? = null,
    descriptionStyle: TextStyle? = null,
    avatarBackgroundModifier: Modifier = Modifier,
    avatarBadgeModifier: Modifier = Modifier,
    avatarContentModifier: Modifier = Modifier,
    avatarType: AvatarEnum = AvatarEnum.ICON,
    listItemStartAvatarSizeEnum: ListItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.ICON,
    avatarBackgroundColor: Color = Color.Transparent,
    avatarBackgroundRadius: Int = 0,
    avatarIcon: Int? = null,
    avatarIconColor: Color? = DigitalTheme.colorScheme.contentPrimary,
    avatarIconPadding: Dp = Dp.Unspecified,
    endIcon: Int? = null,
    endIconColor: Color = DigitalTheme.colorScheme.contentPrimary,
    endIconPadding: Int = 0,
    endIconBackgroundColor: Color = Color.Transparent,
    endIconBackgroundRadius: Int = 4,
    avatarImageUrl: String? = null,
    avatarClipPercent: Int = 0,
    avatarImageCornerRadius: Int? = null,
    avatarContentScale: ContentScale = ContentScale.Crop,
    avatarText: String? = null,
    avatarTextColor: Color = DigitalTheme.colorScheme.contentSecondary,
    avatarBadgeType: BadgeEnum = BadgeEnum.NONE,
    avatarBadgeIcon: Int? = null,
    avatarBadgeBackgroundColor: Color = DigitalTheme.colorScheme.backgroundBrand,
    avatarBadgeIconColor: Color = DigitalTheme.colorScheme.contentSecondary,
    avatarBadgeBorderColor: Color = DigitalTheme.colorScheme.borderSecondary,

    ) {
    ListItem(
        title = title,
        titleMaxLines = titleMaxLines,
        titleColor = titleColor,
        titleStyle = titleStyle,
        description = description,
        descriptionMaxLines = descriptionMaxLines,
        descriptionColor = descriptionColor,
        descriptionStyle = descriptionStyle,
        showDivider = true,
        backgroundColor = Color.Transparent,
        backgroundRadius = 0,
        avatarBackgroundModifier = avatarBackgroundModifier,
        avatarBadgeModifier = avatarBadgeModifier,
        avatarContentModifier = avatarContentModifier,
        avatarType = avatarType,
        listItemStartAvatarSizeEnum = listItemStartAvatarSizeEnum,
        avatarBackgroundColor = avatarBackgroundColor,
        avatarBackgroundRadius = avatarBackgroundRadius,
        avatarIcon = avatarIcon,
        avatarIconColor = avatarIconColor,
        avatarIconPadding = avatarIconPadding,
        endIcon = endIcon,
        endIconColor = endIconColor,
        endIconPadding = endIconPadding,
        endIconBackgroundColor = endIconBackgroundColor,
        endIconBackgroundRadius = endIconBackgroundRadius,
        avatarImageUrl = avatarImageUrl,
        avatarClipPercent = avatarClipPercent,
        avatarImageCornerRadius = avatarImageCornerRadius,
        avatarContentScale = avatarContentScale,
        avatarText = avatarText,
        avatarTextColor = avatarTextColor,
        avatarBadgeType = avatarBadgeType,
        avatarBadgeIcon = avatarBadgeIcon,
        avatarBadgeBackgroundColor = avatarBadgeBackgroundColor,
        avatarBadgeIconColor = avatarBadgeIconColor,
        avatarBadgeBorderColor = avatarBadgeBorderColor,
    )
}

@Composable
private fun MainContent(tableItems: List<Pair<String, String>>) {
    tableItems.forEachIndexed { index, item ->
        TableRow(item.first, item.second, tableItems.size - 1 == index)
    }
}

@Composable
private fun AnimatedContent(
    expanded: MutableState<Boolean>,
    tableItems: List<Pair<String, String>>,
    minimumVisibleItemsCount: Int
) {
    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(
                durationMillis = 500,
                delayMillis = 100,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(
                delayMillis = 100,
                durationMillis = 500,
                easing = LinearOutSlowInEasing
            )
        )
    }

    AnimatedVisibility(
        visible = expanded.value,
        enter = enterTransition,
        exit = exitTransition
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            PrimaryDivider(modifier = Modifier.padding(horizontal = 16.dp))
            MainContent(tableItems.subList(minimumVisibleItemsCount, tableItems.size))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TableRow(title: String, value: String, isLast: Boolean) {
    val context = LocalContext.current
    Row(modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)) {
        PrimaryText(
            text = title,
            modifier = Modifier.weight(0.5f),
            style = DigitalTheme.typography.smallRegular,
            color = DigitalTheme.colorScheme.contentPrimaryTonal1
        )
        HorizontalSpacer(8.dp)
        PrimaryText(
            text = value, modifier = Modifier
                .weight(0.5f)
                .combinedClickable(
                    onClick = {},
                    onLongClick = {
                        context.copyWithVibration(value)
                    }
                ), textAlign = TextAlign.End, style = DigitalTheme.typography.smallBold)
    }
    if (!isLast) {
        PrimaryDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
@PreviewLightDark
fun TableComponentPreview(

) {
    DigitalTheme {
        Column(
            modifier = Modifier
                .background(DigitalTheme.colorScheme.backgroundBase)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .padding(bottom = 50.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            TableComponent(
                title = "5G վարկ",
                avatarIcon = R.drawable.ic_phonebook,
                tableItems = arrayListOf(
                    Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան"),
                    Pair("Վարկային կոդ", "234567890"),
                    Pair("Հաշվեհամար", "123454354553224"),
                    Pair("Հերթական մարում", "4,000.00 AMD"),
                    Pair("Ընթացիկ", "4,000.00 AMD"),
                    Pair("Տոկոսագումար", "4,000.00 AMD"),
                    Pair("Միջնորդավճար", "4,000.00 AMD"),
                    Pair("Միջնորդավճար 2", "4,000.00 AMD"),
                    Pair("Միջնորդավճար 3", "4,000.00 AMD"),
                    Pair("Ընդամենը", "4,000.00 AMD")
                )
            )
            VerticalSpacer(20.dp)
            TableComponent(
                title = "5G վարկ",
                avatarIcon = R.drawable.ic_phonebook,
                tableItems = arrayListOf(
                    Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան"),
                    Pair("Վարկային կոդ", "234567890"),
                    Pair("Հաշվեհամար", "123454354553224"),
                    Pair("Հերթական մարում", "4,000.00 AMD"),
                    Pair("Ընթացիկ", "4,000.00 AMD"),
                    Pair("Տոկոսագումար", "4,000.00 AMD"),
                    Pair("Միջնորդավճար", "4,000.00 AMD")
                )
            )
            VerticalSpacer(20.dp)
            TableComponent(
                title = "5G վարկ",
                avatarIcon = R.drawable.ic_phonebook,
                tableItems = arrayListOf(
                    Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան")
                )
            )
            VerticalSpacer(20.dp)
            TableComponent(
                title = "5G վարկ",
                avatarIcon = R.drawable.default_avatar,
                avatarBackgroundColor = Color.Transparent,
                avatarType = AvatarEnum.IMAGE,
                listItemStartAvatarSizeEnum = ListItemStartAvatarSizeEnum.AVATAR,
                tableItems = arrayListOf(
                    Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան")
                )
            )
            VerticalSpacer(20.dp)
            TableComponent(
                title = "5G վարկ",
                tableItems = arrayListOf(
                    Pair("Անուն Ազգանուն", "Արշակ Մկրտչյան")
                )
            )
            VerticalSpacer(20.dp)
            TableComponent(
                tableItems = arrayListOf(
                    Pair("Վարկային կոդ", "234567890"),
                    Pair("Հաշվեհամար", "123454354553224"),
                    Pair("Հերթական մարում", "4,000.00 AMD"),
                )
            )
        }
    }
}

