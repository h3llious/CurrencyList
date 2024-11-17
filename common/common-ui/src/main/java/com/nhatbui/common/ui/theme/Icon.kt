package com.nhatbui.common.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import com.nhatbui.common.ui.R

internal val LocalIcon = staticCompositionLocalOf { IconResourceProvider() }

class IconResourceProvider {
    val iconRightArrow = R.drawable.ic_right_arrow
    val iconCheck = R.drawable.ic_check
}
