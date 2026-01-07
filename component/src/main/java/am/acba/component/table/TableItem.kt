package am.acba.component.table

import androidx.compose.runtime.Immutable

@Immutable
data class TableItem(
    val fieldTitle: String = "",
    val fieldValue: String = "",
    val fieldValueColor: Int = 0
)