package am.acba.components.models

import android.os.Parcelable

@kotlinx.parcelize.Parcelize
data class ClickListener(
    val onClick: (Boolean) -> Unit
) : Parcelable
