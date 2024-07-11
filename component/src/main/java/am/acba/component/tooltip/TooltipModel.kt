package am.acba.component.tooltip

import androidx.annotation.DrawableRes

data class TooltipModel(
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val lottieAnimationName: String = "",
    @DrawableRes val localImage: Int? = null,
)

