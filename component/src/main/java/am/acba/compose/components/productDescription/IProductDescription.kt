package am.acba.compose.components.productDescription

import am.acba.compose.components.timeLine.ProductDescriptionBadge
import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.ui.graphics.Color

interface IProductDescription {
    val title: String
        get() = EMPTY_STRING
    val subTitle: String
        get() = EMPTY_STRING
    val mediaImage: String
        get() = EMPTY_STRING
    val secondTitle: String
        get() = EMPTY_STRING
    val secondSubTitle: String
        get() = EMPTY_STRING
    val bullets: List<String>
        get() = emptyList()
    val badges: List<ProductDescriptionBadge>
        get() = emptyList()
    val badgeBackgroundColor: Color?
        get() = null
    val badgeTextColor: Color?
        get() = null
}