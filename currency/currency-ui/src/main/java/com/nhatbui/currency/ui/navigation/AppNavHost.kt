package com.nhatbui.currency.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nhatbui.currency.ui.CurrencyScreen

@Composable
internal fun AppNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.Currency
    ) {
        composable<Route.Currency> {
            CurrencyScreen()
        }
    }
}
