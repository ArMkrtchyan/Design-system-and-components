package am.acba.component.loanComponents

import androidx.annotation.AttrRes

data class LoanCard(
    val title: String = "",
    val amount: String = "",
    val currency: String = "",
    val endDate: String = "",
    val isNewBadgeVisible: Boolean = false,
    @AttrRes val backgroundColorAttr: Int = 0
)
