package com.nhatbui.common.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalDimension = staticCompositionLocalOf { CurrencyListDimensions() }

data class CurrencyListDimensions(
    val spacingMedium: Dp = 8.dp,
    val spacingLarge: Dp = 16.dp
)
