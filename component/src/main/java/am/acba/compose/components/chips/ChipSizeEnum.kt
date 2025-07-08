package am.acba.compose.components.chips

import am.acba.compose.components.avatar.AvatarSizeEnum
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class ChipSizeEnum(
    val height: Dp,
    val minWidth: Dp,
    val avatarSpacerWidth: Int,
    val paddingHorizontal: Dp,
    val endIconSize: Dp,
    val avatarSizeForImage: AvatarSizeEnum,
    val avatarSizeForIcon: AvatarSizeEnum
) {
    SMALL(32.dp, 52.dp, 8, 4.dp, 16.dp, AvatarSizeEnum.AVATAR_SIZE_24, AvatarSizeEnum.AVATAR_SIZE_16),
    LARGE(48.dp, 82.dp, 4, 8.dp, 24.dp, AvatarSizeEnum.AVATAR_SIZE_32, AvatarSizeEnum.AVATAR_SIZE_24);
}