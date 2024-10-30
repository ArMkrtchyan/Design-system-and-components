package am.acba.components.models

import am.acba.acbamobile.ui.kotlin.screens.loan.IChipModel

data class RepaymentFrequency(val id: Int = 0, val frequency: String = "") : IChipModel {
    override fun getTitle(): String {
        return frequency
    }
}
