package am.acba.compose.components.avatar

import am.acba.compose.theme.DigitalTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AvatarSizeEnum(
    val size: Dp,
    val dotBadgeSize: Dp,
    val dotBadgePadding: Dp,
    val iconBadgeSize: Dp,
    val iconBadgePadding: Dp,
) {
    AVATAR_SIZE_16(16.dp, 0.dp, 0.dp, 0.dp, 0.dp),
    AVATAR_SIZE_24(24.dp, 0.dp, 0.dp, 0.dp, 0.dp),
    AVATAR_SIZE_26(26.dp, 0.dp, 0.dp, 0.dp, 0.dp),
    AVATAR_SIZE_32(32.dp, 11.dp, 0.dp, 0.dp, 0.dp),
    AVATAR_SIZE_36(36.dp, 11.dp, 0.dp, 14.dp, 0.dp),
    AVATAR_SIZE_40(40.dp, 12.dp, 0.dp, 16.dp, 0.dp),
    AVATAR_SIZE_56(56.dp, 16.dp, 0.dp, 24.dp, 2.dp),
    AVATAR_SIZE_80(80.dp, 0.dp, 0.dp, 32.dp, 6.dp);


    @Composable
    fun getTextStyle() = when (this) {
        AVATAR_SIZE_16 -> DigitalTheme.typography.xSmallRegular
        AVATAR_SIZE_24 -> DigitalTheme.typography.xSmallRegular
        AVATAR_SIZE_26 -> DigitalTheme.typography.xSmallRegular
        AVATAR_SIZE_32 -> DigitalTheme.typography.smallRegular
        AVATAR_SIZE_36 -> DigitalTheme.typography.subTitle2Regular
        AVATAR_SIZE_40 -> DigitalTheme.typography.subTitle1Regular
        AVATAR_SIZE_56 -> DigitalTheme.typography.heading6Regular
        AVATAR_SIZE_80 -> DigitalTheme.typography.heading3Regular
    }
}