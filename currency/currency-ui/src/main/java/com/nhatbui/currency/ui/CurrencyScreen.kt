package com.nhatbui.currency.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.nhatbui.currency.presentation.CurrencyViewModel

@Composable
fun CurrencyScreen(modifier: Modifier = Modifier) {
    val viewModel: CurrencyViewModel = hiltViewModel()
    Text(
        text = "Hello ${viewModel.name}!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CurrencyScreen()
}
