package am.acba.component.phoneNumberInput

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CountryModel(
    val englishName: String? = "",
    val flagResId: Int = 0,
    val name: String? = "",
    val currency: String? = "",
    val nameCode: String? = "",
    val phoneCode: String? = "",
    var isSelected: Boolean = false,
    var previouslyItemSelectedPosition: Boolean = false
) : Parcelable




