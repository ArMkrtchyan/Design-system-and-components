package am.acba.compose.components.productDescription

import androidx.compose.ui.graphics.Color

interface IProductDescription {
    val title: String
        get() = ""
    val subTitle: String
        get() = ""
    val mediaImage: String
        get() = ""
    val secondTitle: String
        get() = ""
    val secondSubTitle: String
        get() = ""
    val bullets: List<String>
        get() = emptyList()
    val badges: List<Pair<String, String>>
        get() = emptyList()
    val badgeBackgroundColor: Color?
        get() = null
    val badgeTextColor: Color?
        get() = null
}