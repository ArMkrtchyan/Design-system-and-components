﻿package am.acba.compose.components.chips

import am.acba.compose.theme.DigitalTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class ChipStateEnum {
    NOT_SELECTED,
    SELECTED;

    @Composable
    fun getContentColor(): Color {
        return when (this) {
            NOT_SELECTED -> DigitalTheme.colorScheme.contentPrimary
            SELECTED -> DigitalTheme.colorScheme.contentInverse2
        }
    }

    @Composable
    fun getBackgroundColor(): Color {
        return when (this) {
            NOT_SELECTED -> DigitalTheme.colorScheme.backgroundTonal2
            SELECTED -> DigitalTheme.colorScheme.contentNeutralTonal1
        }
    }
}