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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nhatbui.common.ui.component.Screen
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.presentation.CurrencyViewModel
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import com.nhatbui.currency.presentation.model.CurrencyRequestPresentationModel
import com.nhatbui.currency.ui.mapper.CurrencyPresentationToUiMapper
import com.nhatbui.currency.ui.model.CurrencyUiModel
import com.nhatbui.currency.ui.model.CurrencyUiModel.Crypto
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun CurrencyScreen() = Screen<CurrencyPresentationState, CurrencyViewModel> {
    val currencyUiMapper = remember { CurrencyPresentationToUiMapper() }
    LaunchedEffect(Unit) {
        viewModel.insertCurrencies()
        viewModel.getCurrencies(CurrencyRequestPresentationModel.All)
    }
    Content { viewState ->
        val currencies = viewState.currencies.map(currencyUiMapper::map)
        CurrencyScreenContent(
            currencies = currencies.toImmutableList()
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
                .size(36.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(CurrencyTheme.dimensions.spacingLarge)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = item.iconInitial,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.labelLarge
            )
        }
        Spacer(modifier = Modifier.width(CurrencyTheme.dimensions.spacingMedium))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.basicMarquee(),
                text = item.name,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )
        }
        if (item is Crypto) {
            Spacer(modifier = Modifier.width(CurrencyTheme.dimensions.spacingMedium))
            Text(
                text = item.symbol,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(CurrencyTheme.dimensions.spacingMedium))
            Icon(
                modifier = Modifier.size(CurrencyTheme.dimensions.spacingMedium),
                painter = painterResource(CurrencyTheme.icons.iconRightArrow),
                tint = MaterialTheme.colorScheme.tertiary,
                contentDescription = null
            )
        }
    }
}
