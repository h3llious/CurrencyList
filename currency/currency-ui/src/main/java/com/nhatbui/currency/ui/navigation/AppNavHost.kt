package com.nhatbui.currency.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nhatbui.currency.ui.CurrencyScreen
import com.nhatbui.currency.ui.di.NavHostDependencies

@Composable
internal fun NavHostDependencies.AppNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Route.Currency
    ) {
        composable<Route.Currency> {
            currencyDependencies.CurrencyScreen()
        }
    }
}
