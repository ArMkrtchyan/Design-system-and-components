package am.acba.compose.components.avatar

import am.acba.compose.theme.DigitalTheme
import androidx.compose.runtime.Composable

enum class AvatarSizeEnum(
    val size: Int,
    val dotBadgeSize: Int,
    val dotBadgePadding: Int,
    val iconBadgeSize: Int,
    val iconBadgePadding: Int,
) {
    AVATAR_SIZE_24(24, 0, 0, 0, 0),
    AVATAR_SIZE_32(32, 11, 0, 0, 0),
    AVATAR_SIZE_36(36, 11, 0, 14, 0),
    AVATAR_SIZE_40(40, 12, 0, 16, 0),
    AVATAR_SIZE_56(56, 16, 0, 24, 2),
    AVATAR_SIZE_80(80, 0, 0, 32, 6);


    @Composable
    fun getTextStyle() = when (this) {
        AVATAR_SIZE_24 -> DigitalTheme.typography.xSmallRegular
        AVATAR_SIZE_32 -> DigitalTheme.typography.smallRegular
        AVATAR_SIZE_36 -> DigitalTheme.typography.subTitle2Regular
        AVATAR_SIZE_40 -> DigitalTheme.typography.subTitle1Regular
        AVATAR_SIZE_56 -> DigitalTheme.typography.heading6Regular
        AVATAR_SIZE_80 -> DigitalTheme.typography.heading3Regular
    }
}