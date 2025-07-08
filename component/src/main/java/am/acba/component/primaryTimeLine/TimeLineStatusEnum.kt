package am.acba.component.primaryTimeLine

import am.acba.component.R

enum class TimeLineStatusEnum(
    val type: Int,
    val icon: Int,
    val iconTint: Int,
    val iconBackground: Int = R.drawable.background_rounded,
    val iconBackgroundTint: Int = R.attr.transparent,
    val contentBackgroundTint: Int = R.attr.transparent,
    val linkTextColor: Int = R.attr.contentPrimary,
) {
    NONE(
        type = 0,
        icon = R.drawable.ic_ellipse,
        iconTint = R.attr.borderPrimaryTonal3,
    ),
    DEFAULT(
        type = 1,
        icon = R.drawable.ic_ellipse,
        iconTint = R.attr.borderPrimaryTonal3,
        contentBackgroundTint = R.attr.backgroundTonal2,
        linkTextColor = R.attr.contentInfoTonal1
    ),
    PENDING(
        type = 2,
        icon = R.drawable.ic_info_1,
        iconTint = R.attr.borderInfoTonal1,
        iconBackground = R.drawable.ic_ellipse,
        iconBackgroundTint = R.attr.borderInfoTonal1,
        contentBackgroundTint = R.attr.backgroundTonal2,
        linkTextColor = R.attr.contentInfoTonal1
    ),
    PENDING_2(
        type = 3,
        icon = R.drawable.ic_info_1,
        iconTint = R.attr.contentSecondary,
        iconBackground = R.drawable.background_rounded,
        iconBackgroundTint = R.attr.borderInfoTonal1,
        contentBackgroundTint = R.attr.backgroundInfoTonal1,
        linkTextColor = R.attr.contentInfoTonal1
    ),
    WARNING(
        type = 4,
        icon = R.drawable.ic_attention_1,
        iconTint = R.attr.contentInverse,
        iconBackground = R.drawable.background_rounded,
        iconBackgroundTint = R.attr.backgroundWarning,
        contentBackgroundTint = R.attr.backgroundWarningTonal1,
        linkTextColor = R.attr.contentWarningTonal1
    ),
    DANGER(
        type = 5,
        icon = R.drawable.ic_attention_1,
        iconTint = R.attr.contentSecondary,
        iconBackground = R.drawable.background_rounded,
        iconBackgroundTint = R.attr.backgroundDanger,
        contentBackgroundTint = R.attr.backgroundDangerTonal1,
        linkTextColor = R.attr.contentDangerTonal1
    ),
    SUCCESS(
        type = 6,
        icon = R.drawable.ic_success_small,
        iconTint = R.attr.contentSecondary,
        iconBackground = R.drawable.background_rounded,
        iconBackgroundTint = R.attr.backgroundSuccess,
        contentBackgroundTint = R.attr.backgroundSuccessTonal1,
        linkTextColor = R.attr.contentSuccessTonal1
    );
}