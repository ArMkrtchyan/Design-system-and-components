package am.acba.composeComponents.cards

import am.acba.component.R
import am.acba.compose.common.VerticalSpacer
import am.acba.compose.components.PrimaryButton
import am.acba.compose.components.PrimaryToolbar
import am.acba.compose.components.badges.BadgeEnum
import am.acba.compose.components.cardListing.CardsItem
import am.acba.compose.theme.DigitalTheme
import am.acba.utils.extensions.id
import android.view.Gravity
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsItemScreen(title: String = "") {
    var isEditing by remember { mutableStateOf(false) }
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
                VerticalSpacer(8.dp)
                CardsItem(
                    modifier = Modifier.id("card1"),
                    isEditingInitial = isEditing,
                    title = "Mastercard Standard",
                    subTitle = "Aramayis Ter-Stepanyan",
                    cardNumber = "**** 5678",
                    badgeText = "Badge",
                    endIcon = R.drawable.ic_info,
                    badgeType = BadgeEnum.INFO,
                    swipeActionText = "Բլոկավորել",
                    imageUrl =
                        "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType41_1_1.png",
                    cardStatusIcon = R.drawable.ic_flake
                )
                VerticalSpacer(8.dp)
                CardsItem(
                    modifier = Modifier.id("card2"),
                    title = "Mastercard Standard",
                    isEditingInitial = isEditing,
                    cardNumber = "**** 5678",
                    badgeText = "Լրացուցիչ",
                    endIcon = R.drawable.ic_info,
                    badgeType = BadgeEnum.INFO,
                    imageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType46_1_1.png",
                    statusTitle = "Քարտը պատրաստ է ակտիվացման",
                    statusTextColor = DigitalTheme.colorScheme.contentInfoTonal1,
                    statusIcon = R.drawable.ic_info,
                    statusIconColor = DigitalTheme.colorScheme.contentInfoTonal1,
                    statusBackgroundColor = DigitalTheme.colorScheme.backgroundInfoTonal1,
                    swipeActionText = "Բլոկավորել"
                )
                VerticalSpacer(8.dp)
                CardsItem(
                    modifier = Modifier.id("card2"),
                    title = "***** AMD",
                    titleStyle = DigitalTheme.typography.body1Bold,
                    subTitle = "Aramayis Ter-Stepanyan",
                    isEditingInitial = isEditing,
                    cardNumber = "**** 5678",
                    endIcon = R.drawable.ic_info,
                    badgeType = BadgeEnum.INFO,
                    imageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType46_1_1.png",
                    statusTextColor = DigitalTheme.colorScheme.contentInfoTonal1,
                    statusIcon = R.drawable.ic_info,
                    statusIconColor = DigitalTheme.colorScheme.contentInfoTonal1,
                    statusBackgroundColor = DigitalTheme.colorScheme.backgroundInfoTonal1,
                    swipeActionText = "Բլոկավորել"
                )
                VerticalSpacer(8.dp)
                CardsItem(
                    modifier = Modifier.id("card2"),
                    title = "10,000.00 AMD",
                    titleStyle = DigitalTheme.typography.body1Bold,
                    subTitle = "Aramayis Ter-Stepanyan",
                    isEditingInitial = isEditing,
                    cardNumber = "**** 5678",
                    endIcon = R.drawable.ic_info,
                    badgeType = BadgeEnum.INFO,
                    imageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType41_1_1.png",
                    statusTextColor = DigitalTheme.colorScheme.contentInfoTonal1,
                    statusIcon = R.drawable.ic_info,
                    statusIconColor = DigitalTheme.colorScheme.contentInfoTonal1,
                    statusBackgroundColor = DigitalTheme.colorScheme.backgroundInfoTonal1,
                    swipeActionText = "Բլոկավորել"
                )
                VerticalSpacer(8.dp)
                CardsItem(
                    modifier = Modifier.id("card2"),
                    title = "Evocabank",
                    isEditingInitial = isEditing,
                    cardNumber = "**** 5678",
                    endIcon = R.drawable.ic_info,
                    badgeType = BadgeEnum.INFO,
                    swipeActionIcon = R.drawable.ic_trash,
                    actionBackgroundColor = DigitalTheme.colorScheme.backgroundDanger,
                    imageUrl = "https://online1-test.acba.am/Shared/CardImages/PhysicalCards/CardType41_1_1.png",
                    statusTextColor = DigitalTheme.colorScheme.contentInfoTonal1,
                    statusIcon = R.drawable.ic_info,
                    statusIconColor = DigitalTheme.colorScheme.contentInfoTonal1,
                    statusBackgroundColor = DigitalTheme.colorScheme.backgroundInfoTonal1,
                    swipeActionText = "Հեռացնել"
                )
                VerticalSpacer(20.dp)
                PrimaryButton(
                    onClick = { isEditing = true },
                    text = "play jiggle",
                    iconGravity = Gravity.START,
                )
                VerticalSpacer(20.dp)
                PrimaryButton(
                    onClick = { isEditing = false },
                    text = "cancel jiggle",
                    iconGravity = Gravity.START,
                )
            }
        }
    }
}