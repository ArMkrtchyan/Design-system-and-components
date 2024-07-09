package am.acba.component.alert

import android.view.View
import androidx.annotation.DrawableRes

data class Alert(
    val type: PrimaryAlert.AlertTypes,
    val showCloseIcon: Boolean = false,
    val isFilledBackground: Boolean = false,
    val title: String,
    val body: String = "",
    val link: String = "",
    val onLinkClick: ((View) -> Unit)? = null,
    @DrawableRes val neutralIcon: Int? = null
)