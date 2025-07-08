package am.acba.composeComponents.cardListing

import am.acba.compose.VerticalSpacer
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.avatar.AvatarEnum
import am.acba.compose.components.cardListing.CardListItem
import am.acba.compose.components.divider.PrimaryDivider
import am.acba.compose.components.listItem.ControllerTypeEnum
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListingsScreen(title: String = "") {
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
                    .padding(top = 16.dp)
                    .padding(bottom = 50.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                PrimaryDivider("Only text")
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562 jh db dsbhdsh sdbdsc sdjsdb",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    showBorder = true,
                    backgroundColor = Color.Transparent
                )
                VerticalSpacer(20)
                PrimaryDivider("With icon")
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarIcon = am.acba.component.R.drawable.ic_info,
                    avatarIconPadding = 6.dp,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2,
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarIcon = am.acba.component.R.drawable.ic_info,
                    avatarIconPadding = 6.dp,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2,
                    showBorder = true,
                    backgroundColor = Color.Transparent
                )
                VerticalSpacer(20)
                PrimaryDivider("With media")
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarType = AvatarEnum.IMAGE,
                    avatarImageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType37_1_1.png",
                    avatarImageCornerRadius = 4,
                    avatarContentScale = ContentScale.FillWidth,
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarType = AvatarEnum.IMAGE,
                    avatarImageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType37_1_1.png",
                    avatarImageCornerRadius = 4,
                    avatarContentScale = ContentScale.FillWidth,
                    showBorder = true,
                    backgroundColor = Color.Transparent
                )
                VerticalSpacer(20)
                PrimaryDivider("One line")
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    showBorder = true,
                    backgroundColor = Color.Transparent
                )
                VerticalSpacer(20)
                PrimaryDivider("One line with icon/media")
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarType = AvatarEnum.IMAGE,
                    avatarImageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType37_1_1.png",
                    avatarImageCornerRadius = 4,
                    avatarContentScale = ContentScale.FillWidth,
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarType = AvatarEnum.IMAGE,
                    avatarImageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType37_1_1.png",
                    avatarImageCornerRadius = 4,
                    avatarContentScale = ContentScale.FillWidth,
                    showBorder = true,
                    backgroundColor = Color.Transparent
                )
                VerticalSpacer(20)
                PrimaryDivider("One line right action")
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    endIcon = am.acba.component.R.drawable.ic_down
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    endIcon = am.acba.component.R.drawable.ic_down,
                    showBorder = true,
                    backgroundColor = Color.Transparent
                )
                VerticalSpacer(20)
                PrimaryDivider("With status")
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarIcon = am.acba.component.R.drawable.ic_info,
                    avatarIconPadding = 6.dp,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2,
                    statusTitle = "Հօգուտ`Անի Փ, Աստղիկ, Համո, Աննա, Լաուրա, Թիմային",
                    statusIcon = am.acba.component.R.drawable.ic_user
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarIcon = am.acba.component.R.drawable.ic_info,
                    avatarIconPadding = 6.dp,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2,
                    showBorder = true,
                    backgroundColor = Color.Transparent,
                    statusTitle = "Հօգուտ`Անի Փ, Աստղիկ, Համո, Աննա, Լաուրա, Թիմային",
                    statusIcon = am.acba.component.R.drawable.ic_user
                )
                VerticalSpacer(20)
                PrimaryDivider("With 3 texts")
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarIcon = am.acba.component.R.drawable.ic_info,
                    avatarIconPadding = 6.dp,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2,
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarIcon = am.acba.component.R.drawable.ic_info,
                    avatarIconPadding = 6.dp,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2,
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarIcon = am.acba.component.R.drawable.ic_info,
                    avatarIconPadding = 6.dp,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2,
                    showBorder = true,
                    backgroundColor = Color.Transparent,
                )
                VerticalSpacer(20)
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    avatarIcon = am.acba.component.R.drawable.ic_info,
                    avatarIconPadding = 6.dp,
                    avatarBackgroundColor = DigitalTheme.colorScheme.backgroundTonal2,
                    showBorder = true,
                    backgroundColor = Color.Transparent,
                )
                VerticalSpacer(20)
                PrimaryDivider("With controllers")
                VerticalSpacer(20)
                val checkBoxState = remember { mutableStateOf(false) }
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    controllerType = ControllerTypeEnum.CHECK_BOX,
                    controllerSelected = checkBoxState.value,
                    onCheckedChangeListener = {
                        checkBoxState.value = it
                    }
                )
                VerticalSpacer(20)
                val switchState = remember { mutableStateOf(false) }
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    controllerType = ControllerTypeEnum.SWITCH,
                    controllerSelected = switchState.value,
                    onCheckedChangeListener = {
                        switchState.value = it
                    }
                )
                VerticalSpacer(20)
                val radioButtonState = remember { mutableStateOf(false) }
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    controllerType = ControllerTypeEnum.RADIO_BUTTON,
                    controllerSelected = radioButtonState.value,
                    onRadioButtonClick = {
                        radioButtonState.value = !radioButtonState.value
                    }
                )
                VerticalSpacer(20)
                val checkBoxState2 = remember { mutableStateOf(false) }
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    showBorder = true,
                    backgroundColor = Color.Transparent,
                    controllerType = ControllerTypeEnum.CHECK_BOX,
                    controllerSelected = checkBoxState2.value,
                    onCheckedChangeListener = {
                        checkBoxState2.value = it
                    }
                )
                VerticalSpacer(20)
                val switchState2 = remember { mutableStateOf(false) }
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    showBorder = true,
                    backgroundColor = Color.Transparent,
                    controllerType = ControllerTypeEnum.SWITCH,
                    controllerSelected = switchState2.value,
                    onCheckedChangeListener = {
                        switchState2.value = it
                    }
                )
                VerticalSpacer(20)
                val radioButtonState2 = remember { mutableStateOf(false) }
                CardListItem(
                    startTitle = "Դրամային",
                    startDescription = "***3562",
                    endTitle = "հասանելի",
                    endDescription = "400,000,000.00",
                    currency = "AMD",
                    showBorder = true,
                    backgroundColor = Color.Transparent,
                    controllerType = ControllerTypeEnum.RADIO_BUTTON,
                    controllerSelected = radioButtonState2.value,
                    onRadioButtonClick = {
                        radioButtonState2.value = !radioButtonState2.value
                    }
                )
                VerticalSpacer(20)
            }
        }
    }
}

@Composable
@PreviewLightDark
fun AlertsScreenPreview() {
    DigitalTheme {
        CardListingsScreen()
    }
}