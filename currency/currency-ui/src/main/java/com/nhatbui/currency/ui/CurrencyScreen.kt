package com.nhatbui.currency.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nhatbui.common.ui.component.Screen
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.presentation.CurrencyViewModel
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import com.nhatbui.currency.ui.model.CurrencyUiModel
import com.nhatbui.currency.ui.model.CurrencyUiModel.Crypto
import com.nhatbui.currency.ui.model.CurrencyUiModel.Fiat
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun CurrencyScreen() = Screen<CurrencyPresentationState, CurrencyViewModel> {
    Content { viewState ->
        CurrencyScreenContent(
            currencies = placeholderCurrencies().toImmutableList()
        )
    }
}

@Composable
private fun CurrencyScreenContent(
    currencies: ImmutableList<CurrencyUiModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        state = rememberLazyListState()
    ) {
        itemsIndexed(currencies) { _, currency ->
            CurrencyItem(currency)
        }
    }
}

@Composable
private fun CurrencyItem(item: CurrencyUiModel, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CurrencyTheme.dimensions.spacingMedium,
                vertical = CurrencyTheme.dimensions.spacingLarge
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = Color.DarkGray, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.iconInitial,
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.width(CurrencyTheme.dimensions.spacingMedium))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = item.name,
                color = Color.DarkGray,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
        }
        if (item is Crypto) {
            Text(
                modifier = Modifier.padding(start = CurrencyTheme.dimensions.spacingMedium),
                text = item.symbol,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.DarkGray
            )
            Icon(
                modifier = Modifier
                    .padding(start = CurrencyTheme.dimensions.spacingMedium)
                    .size(CurrencyTheme.dimensions.spacingMedium),
                painter = painterResource(CurrencyTheme.icons.iconRightArrow),
                contentDescription = null
            )
        }
    }
}

private fun placeholderCurrencies() = listOf(
    Crypto(id = "BTC", name = "Bitcoin", symbol = "BTC", iconInitial = "B"),
    Crypto(id = "ETH", name = "Ethereum", symbol = "ETH", iconInitial = "E"),
    Crypto(id = "XRP", name = "XRP", symbol = "XRP", iconInitial = "X"),
    Crypto(id = "BCH", name = "Bitcoin Cash", symbol = "BCH", iconInitial = "B"),
    Crypto(id = "LTC", name = "Litecoin", symbol = "LTC", iconInitial = "L"),
    Fiat(id = "SGD", name = "Singapore Dollar", symbol = "$", code = "SGD", iconInitial = "S"),
    Fiat(id = "EUR", name = "Euro", symbol = "€", code = "EUR", iconInitial = "E"),
    Fiat(id = "GBP", name = "British Pound", symbol = "£", code = "GBP", iconInitial = "B"),
    Fiat(id = "HKD", name = "Hong Kong Dollar", symbol = "$", code = "HKD", iconInitial = "H"),
    Fiat(id = "JPY", name = "Japanese Yen", symbol = "¥", code = "JPY", iconInitial = "J"),
    Fiat(id = "AUD", name = "Australian Dollar", symbol = "$", code = "AUD", iconInitial = "A"),
    Fiat(id = "USD", name = "United States Dollar", symbol = "$", code = "USD", iconInitial = "U")
)
