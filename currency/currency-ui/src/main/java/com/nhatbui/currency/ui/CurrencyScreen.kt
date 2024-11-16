package com.nhatbui.currency.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.nhatbui.common.ui.Screen
import com.nhatbui.currency.presentation.CurrencyViewModel
import com.nhatbui.currency.presentation.model.CurrencyPresentationState

@Composable
fun CurrencyScreen(modifier: Modifier = Modifier) =
    Screen<CurrencyPresentationState, CurrencyViewModel> {
        LaunchedEffect(Unit) {
            viewModel.getExampleName()
        }
        Content { viewState ->
            Text(
                text = "Hello ${viewState.name}!",
                modifier = modifier
            )
        }
    }
