package am.acba.compose.components.listItem

import am.acba.compose.components.avatar.AvatarSizeEnum

enum class ListItemStartAvatarSizeEnum(val avatarSize: AvatarSizeEnum) {
    ICON(avatarSize = AvatarSizeEnum.AVATAR_SIZE_24),
    ICON_WITH_BACKGROUND(avatarSize = AvatarSizeEnum.AVATAR_SIZE_36),
    AVATAR(avatarSize = AvatarSizeEnum.AVATAR_SIZE_40)
}