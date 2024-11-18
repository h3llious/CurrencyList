package com.nhatbui.currency.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nhatbui.currency.ui.composable.LocalSnackbarHostState
import com.nhatbui.currency.ui.di.NavHostDependencies
import com.nhatbui.currency.ui.navigation.AppNavHost

@Composable
internal fun FeatureContent(
    navHostDependencies: NavHostDependencies
) {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            CompositionLocalProvider(LocalSnackbarHostState provides snackbarHostState) {
                navHostDependencies.AppNavHost(navController)
            }
        }
    }
}
