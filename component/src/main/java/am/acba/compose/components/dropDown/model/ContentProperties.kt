@file:OptIn(ExperimentalMaterial3Api::class)

package am.acba.compose.components.dropDown.model

import am.acba.utils.Constants.EMPTY_STRING
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheetDefaults
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ContentProperties(
    val title: String = EMPTY_STRING,
    val modalBottomSheetProperties: ModalBottomSheetProperties = ModalBottomSheetDefaults.properties,
    val horizontalPadding: Dp = 16.dp,
    val bottomPadding: Dp = 16.dp,
    val calculatePercentForOpenFullScreen: Boolean = true,
)