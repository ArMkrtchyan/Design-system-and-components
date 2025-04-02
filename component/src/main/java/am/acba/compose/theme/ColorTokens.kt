package am.acba.compose.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Stable
class ColorTokens(
    backgroundBase: Color,
    backgroundBrand: Color,
    backgroundBrandPressed: Color,
    backgroundBrandDisable: Color,
    backgroundTonal1: Color,
    backgroundTonal2: Color,
    backgroundTonal3: Color,
    backgroundTonal6: Color,
    backgroundTonal2ReadOnly: Color,
    backgroundTonal2Disable: Color,
    backgroundDanger: Color,
    backgroundDangerTonal1: Color,
    backgroundSuccess: Color,
    backgroundSuccessTonal1: Color,
    backgroundSecondary: Color,
    backgroundSecondaryPressed: Color,
    backgroundInfo: Color,
    backgroundInfoTonal1: Color,
    backgroundWarning: Color,
    backgroundWarningTonal1: Color,
    backgroundPending: Color,
    backgroundAlternative: Color,
    backgroundAlternative2: Color,
    backgroundAlternative3: Color,
    backgroundAlternative4: Color,
    backgroundAlternative5: Color,
    backgroundAlternative6: Color,
    backgroundAlternative7: Color,
    backgroundAlternative8: Color,
    backgroundAlternative9: Color,
    backgroundInverse: Color,
    backgroundNavBar: Color,
    borderBase: Color,
    borderPrimary: Color,
    borderPrimaryTonal1: Color,
    borderPrimaryTonal2: Color,
    borderBrand: Color,
    borderBrandTonal1: Color,
    borderBrandTonal1Disable: Color,
    borderSecondary: Color,
    borderNeutral: Color,
    borderDanger: Color,
    borderDangerTonal1: Color,
    borderSuccess: Color,
    borderWarning: Color,
    borderInfoTonal1: Color,
    overlayTonal1: Color,
    overlayTonal2: Color,
    overlayTonal3: Color,
    overlayTonal4: Color,
    overlayBackground: Color,
    overlayBackgroundTonal1: Color,
    overlayBackgroundTonal2: Color,
    overlayBackgroundTonal5: Color,
    overlayBackgroundTonal6: Color,
    overlayBackgroundTonal7: Color,
    overlayBackgroundTonal8: Color,
    contentBrand: Color,
    contentBrandTonal1: Color,
    contentBrandTonal1Disable: Color,
    contentPrimary: Color,
    contentPrimaryDisable: Color,
    contentPrimaryTonal1: Color,
    contentPrimaryTonal2: Color,
    contentPrimaryTonal1Disable: Color,
    contentSecondary: Color,
    contentSecondaryDisable: Color,
    contentInverse: Color,
    contentNeutralTonal1: Color,
    contentDangerTonal1: Color,
    contentInfoTonal1: Color,
    contentSuccessTonal1: Color,
    contentWarning: Color,
    contentWarningTonal1: Color,
    contentWarningTonal2: Color,
    contentPending: Color,
    contentAlternative: Color,
    contentAlternative2: Color,
    contentAlternative3: Color,
    contentAlternative4: Color,
    contentAlternative5: Color,
    contentAlternative6: Color,
) {
    var backgroundBase by mutableStateOf(backgroundBase)
        private set
    var backgroundBrand by mutableStateOf(backgroundBrand)
        private set
    var backgroundBrandPressed by mutableStateOf(backgroundBrandPressed)
        private set
    var backgroundBrandDisable by mutableStateOf(backgroundBrandDisable)
        private set
    var backgroundTonal1 by mutableStateOf(backgroundTonal1)
        private set
    var backgroundTonal2 by mutableStateOf(backgroundTonal2)
        private set
    var backgroundTonal3 by mutableStateOf(backgroundTonal3)
        private set
    var backgroundTonal6 by mutableStateOf(backgroundTonal6)
        private set
    var backgroundTonal2ReadOnly by mutableStateOf(backgroundTonal2ReadOnly)
        private set
    var backgroundTonal2Disable by mutableStateOf(backgroundTonal2Disable)
        private set
    var backgroundDanger by mutableStateOf(backgroundDanger)
        private set
    var backgroundDangerTonal1 by mutableStateOf(backgroundDangerTonal1)
        private set
    var backgroundSuccess by mutableStateOf(backgroundSuccess)
        private set
    var backgroundSuccessTonal1 by mutableStateOf(backgroundSuccessTonal1)
        private set
    var backgroundSecondary by mutableStateOf(backgroundSecondary)
        private set
    var backgroundSecondaryPressed by mutableStateOf(backgroundSecondary)
        private set
    var backgroundInfo by mutableStateOf(backgroundInfo)
        private set
    var backgroundInfoTonal1 by mutableStateOf(backgroundInfoTonal1)
        private set
    var backgroundWarning by mutableStateOf(backgroundWarning)
        private set
    var backgroundWarningTonal1 by mutableStateOf(backgroundWarningTonal1)
        private set
    var backgroundPending by mutableStateOf(backgroundPending)
        private set
    var backgroundAlternative by mutableStateOf(backgroundAlternative)
        private set
    var backgroundAlternative2 by mutableStateOf(backgroundAlternative2)
        private set
    var backgroundAlternative3 by mutableStateOf(backgroundAlternative3)
        private set
    var backgroundAlternative4 by mutableStateOf(backgroundAlternative4)
        private set
    var backgroundAlternative5 by mutableStateOf(backgroundAlternative5)
        private set
    var backgroundAlternative6 by mutableStateOf(backgroundAlternative6)
        private set
    var backgroundAlternative7 by mutableStateOf(backgroundAlternative7)
        private set
    var backgroundAlternative8 by mutableStateOf(backgroundAlternative8)
        private set
    var backgroundAlternative9 by mutableStateOf(backgroundAlternative9)
        private set
    var backgroundInverse by mutableStateOf(backgroundInverse)
        private set
    var backgroundNavBar by mutableStateOf(backgroundNavBar)
        private set

    var borderBase by mutableStateOf(borderBase)
        private set
    var borderPrimary by mutableStateOf(borderPrimary)
        private set
    var borderPrimaryTonal1 by mutableStateOf(borderPrimaryTonal1)
        private set
    var borderPrimaryTonal2 by mutableStateOf(borderPrimaryTonal2)
        private set
    var borderBrand by mutableStateOf(borderBrand)
        private set
    var borderBrandTonal1 by mutableStateOf(borderBrandTonal1)
        private set
    var borderBrandTonal1Disable by mutableStateOf(borderBrandTonal1Disable)
        private set
    var borderSecondary by mutableStateOf(borderSecondary)
        private set
    var borderNeutral by mutableStateOf(borderNeutral)
        private set
    var borderDanger by mutableStateOf(borderDanger)
        private set
    var borderDangerTonal1 by mutableStateOf(borderDangerTonal1)
        private set
    var borderSuccess by mutableStateOf(borderSuccess)
        private set
    var borderWarning by mutableStateOf(borderWarning)
        private set
    var borderInfoTonal1 by mutableStateOf(borderInfoTonal1)
        private set
    var overlayTonal1 by mutableStateOf(overlayTonal1)
        private set
    var overlayTonal2 by mutableStateOf(overlayTonal2)
        private set
    var overlayTonal3 by mutableStateOf(overlayTonal3)
        private set
    var overlayTonal4 by mutableStateOf(overlayTonal4)
        private set
    var overlayBackground by mutableStateOf(overlayBackground)
        private set
    var overlayBackgroundTonal1 by mutableStateOf(overlayBackgroundTonal1)
        private set
    var overlayBackgroundTonal2 by mutableStateOf(overlayBackgroundTonal2)
        private set
    var overlayBackgroundTonal5 by mutableStateOf(overlayBackgroundTonal5)
        private set
    var overlayBackgroundTonal6 by mutableStateOf(overlayBackgroundTonal6)
        private set
    var overlayBackgroundTonal7 by mutableStateOf(overlayBackgroundTonal7)
        private set
    var overlayBackgroundTonal8 by mutableStateOf(overlayBackgroundTonal8)
        private set

    var contentBrand by mutableStateOf(contentBrand)
        private set
    var contentBrandTonal1 by mutableStateOf(contentBrandTonal1)
        private set
    var contentBrandTonal1Disable by mutableStateOf(contentBrandTonal1Disable)
        private set
    var contentPrimary by mutableStateOf(contentPrimary)
        private set
    var contentPrimaryDisable by mutableStateOf(contentPrimaryDisable)
        private set
    var contentPrimaryTonal1 by mutableStateOf(contentPrimaryTonal1)
        private set
    var contentPrimaryTonal2 by mutableStateOf(contentPrimaryTonal2)
        private set
    var contentPrimaryTonal1Disable by mutableStateOf(contentPrimaryTonal1Disable)
        private set
    var contentSecondary by mutableStateOf(contentSecondary)
        private set
    var contentSecondaryDisable by mutableStateOf(contentSecondaryDisable)
        private set
    var contentInverse by mutableStateOf(contentInverse)
        private set
    var contentNeutralTonal1 by mutableStateOf(contentNeutralTonal1)
        private set
    var contentDangerTonal1 by mutableStateOf(contentDangerTonal1)
        private set
    var contentInfoTonal1 by mutableStateOf(contentInfoTonal1)
        private set
    var contentSuccessTonal1 by mutableStateOf(contentSuccessTonal1)
        private set
    var contentWarning by mutableStateOf(contentWarning)
        private set
    var contentWarningTonal1 by mutableStateOf(contentWarningTonal1)
        private set
    var contentWarningTonal2 by mutableStateOf(contentWarningTonal2)
        private set
    var contentPending by mutableStateOf(contentPending)
        private set
    var contentAlternative by mutableStateOf(contentAlternative)
        private set
    var contentAlternative2 by mutableStateOf(contentAlternative2)
        private set
    var contentAlternative3 by mutableStateOf(contentAlternative3)
        private set
    var contentAlternative4 by mutableStateOf(contentAlternative4)
        private set
    var contentAlternative5 by mutableStateOf(contentAlternative5)
        private set
    var contentAlternative6 by mutableStateOf(contentAlternative6)
        private set

    fun update(oldColors: ColorTokens) {
        backgroundBase = oldColors.backgroundBase
        backgroundBrand = oldColors.backgroundBrand
        backgroundBrandPressed = oldColors.backgroundBrandPressed
        backgroundBrandDisable = oldColors.backgroundBrandDisable
        backgroundTonal1 = oldColors.backgroundTonal1
        backgroundTonal2 = oldColors.backgroundTonal2
        backgroundTonal3 = oldColors.backgroundTonal3
        backgroundTonal6 = oldColors.backgroundTonal6
        backgroundTonal2ReadOnly = oldColors.backgroundTonal2ReadOnly
        backgroundTonal2Disable = oldColors.backgroundTonal2Disable
        backgroundDanger = oldColors.backgroundDanger
        backgroundDangerTonal1 = oldColors.backgroundDangerTonal1
        backgroundSuccess = oldColors.backgroundSuccess
        backgroundSuccessTonal1 = oldColors.backgroundSuccessTonal1
        backgroundSecondary = oldColors.backgroundSecondary
        backgroundSecondaryPressed = oldColors.backgroundSecondaryPressed
        backgroundInfo = oldColors.backgroundInfo
        backgroundInfoTonal1 = oldColors.backgroundInfoTonal1
        backgroundWarning = oldColors.backgroundWarning
        backgroundWarningTonal1 = oldColors.backgroundWarningTonal1
        backgroundPending = oldColors.backgroundPending
        backgroundAlternative = oldColors.backgroundAlternative
        backgroundAlternative2 = oldColors.backgroundAlternative2
        backgroundAlternative3 = oldColors.backgroundAlternative3
        backgroundAlternative4 = oldColors.backgroundAlternative4
        backgroundAlternative5 = oldColors.backgroundAlternative5
        backgroundAlternative6 = oldColors.backgroundAlternative6
        backgroundAlternative7 = oldColors.backgroundAlternative7
        backgroundAlternative8 = oldColors.backgroundAlternative8
        backgroundAlternative9 = oldColors.backgroundAlternative9
        backgroundInverse = oldColors.backgroundInverse
        backgroundNavBar = oldColors.backgroundNavBar
        borderBase = oldColors.borderBase
        borderPrimary = oldColors.borderPrimary
        borderPrimaryTonal1 = oldColors.borderPrimaryTonal1
        borderPrimaryTonal2 = oldColors.borderPrimaryTonal2
        borderBrand = oldColors.borderBrand
        borderBrandTonal1 = oldColors.borderBrandTonal1
        borderBrandTonal1Disable = oldColors.borderBrandTonal1Disable
        borderSecondary = oldColors.borderSecondary
        borderNeutral = oldColors.borderNeutral
        borderDanger = oldColors.borderDanger
        borderDangerTonal1 = oldColors.borderDangerTonal1
        borderSuccess = oldColors.borderSuccess
        borderWarning = oldColors.borderWarning
        borderInfoTonal1 = oldColors.borderInfoTonal1
        overlayTonal1 = oldColors.overlayTonal1
        overlayTonal2 = oldColors.overlayTonal2
        overlayTonal3 = oldColors.overlayTonal3
        overlayTonal4 = oldColors.overlayTonal4
        overlayBackground = oldColors.overlayBackground
        overlayBackgroundTonal1 = oldColors.overlayBackgroundTonal1
        overlayBackgroundTonal2 = oldColors.overlayBackgroundTonal2
        overlayBackgroundTonal5 = oldColors.overlayBackgroundTonal5
        overlayBackgroundTonal6 = oldColors.overlayBackgroundTonal6
        overlayBackgroundTonal7 = oldColors.overlayBackgroundTonal7
        overlayBackgroundTonal8 = oldColors.overlayBackgroundTonal8
        contentBrand = oldColors.contentBrand
        contentBrandTonal1 = oldColors.contentBrandTonal1
        contentBrandTonal1Disable = oldColors.contentBrandTonal1Disable
        contentPrimary = oldColors.contentPrimary
        contentPrimaryDisable = oldColors.contentPrimaryDisable
        contentPrimaryTonal1 = oldColors.contentPrimaryTonal1
        contentPrimaryTonal2 = oldColors.contentPrimaryTonal2
        contentPrimaryTonal1Disable = oldColors.contentPrimaryTonal1Disable
        contentSecondary = oldColors.contentSecondary
        contentSecondaryDisable = oldColors.contentSecondaryDisable
        contentInverse = oldColors.contentInverse
        contentNeutralTonal1 = oldColors.contentNeutralTonal1
        contentDangerTonal1 = oldColors.contentDangerTonal1
        contentInfoTonal1 = oldColors.contentInfoTonal1
        contentSuccessTonal1 = oldColors.contentSuccessTonal1
        contentWarning = oldColors.contentWarning
        contentWarningTonal1 = oldColors.contentWarningTonal1
        contentWarningTonal2 = oldColors.contentWarningTonal2
        contentPending = oldColors.contentPending
        contentAlternative = oldColors.contentAlternative
        contentAlternative2 = oldColors.contentAlternative2
        contentAlternative3 = oldColors.contentAlternative3
        contentAlternative4 = oldColors.contentAlternative4
        contentAlternative5 = oldColors.contentAlternative5
        contentAlternative6 = oldColors.contentAlternative6
    }
}
