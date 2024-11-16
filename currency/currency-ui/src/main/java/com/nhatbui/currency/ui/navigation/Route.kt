package com.nhatbui.currency.ui.navigation

import kotlinx.serialization.Serializable

sealed class Route {
    @Serializable
    data object Currency: Route()
}
