package am.acba.component.loanComponents

import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes

data class LoanCard(
    val title: String = "",
    val description: String = "",
    @DrawableRes val startIcon: Int = 0,
    val nextPaymentDay: String = "",
    val nextPaymentAmount: String = "",
    val loanAdditionalInfo: List<LoanCardAdditionalInfo> = emptyList(),
    @AttrRes val backgroundColorAttr: Int = 0
)