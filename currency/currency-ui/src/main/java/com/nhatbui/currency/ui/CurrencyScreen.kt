package com.nhatbui.currency.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nhatbui.common.ui.component.Screen
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.presentation.CurrencyViewModel
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import com.nhatbui.currency.presentation.model.CurrencyTypePresentationModel
import com.nhatbui.currency.ui.composable.FilterBottomSheet
import com.nhatbui.currency.ui.composable.Header
import com.nhatbui.currency.ui.composable.SearchBar
import com.nhatbui.currency.ui.composable.SettingsBottomSheet
import com.nhatbui.currency.ui.di.CurrencyDependencies
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel
import com.nhatbui.currency.ui.model.CurrencyUiModel
import com.nhatbui.currency.ui.model.CurrencyUiModel.Crypto
import com.nhatbui.currency.ui.model.toPresentation
import com.nhatbui.currency.ui.model.toUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun CurrencyDependencies.CurrencyScreen() =
    Screen<CurrencyPresentationState, CurrencyViewModel> {
        var showFilterBottomSheet by remember { mutableStateOf(false) }
        var showSettingsBottomSheet by remember { mutableStateOf(false) }
        var showSearchBar by remember { mutableStateOf(false) }
        var searchText by remember { mutableStateOf("") }
        LaunchedEffect(Unit) {
            viewModel.getCurrencies(CurrencyTypePresentationModel.All)
        }
        Content { viewState ->
            val currencies = remember(viewState.currencies) {
                viewState.currencies.map(currencyPresentationToUiMapper::map)
            }
            val currencyType = remember(viewState.currencyType) { viewState.currencyType.toUi() }
            CurrencyScreenContent(
                currencyType = currencyType,
                currencies = currencies.toImmutableList(),
                isSearching = showSearchBar,
                onSettingsClick = { showSettingsBottomSheet = true },
                onFilterClick = { showFilterBottomSheet = true },
                onSearchClick = { showSearchBar = true },
                onSearchCancel = {
                    showSearchBar = false
                    searchText = ""
                },
                onSearching = { searchingValue ->
                    searchText = searchingValue
                }
            )
            FilterBottomSheet(
                shouldShowBottomSheet = showFilterBottomSheet,
                onDismiss = { showFilterBottomSheet = false },
                onSelectType = { type ->
                    viewModel.getCurrencies(type.toPresentation())
                },
                currentSelectedType = currencyType
            )
            SettingsBottomSheet(
                shouldShowBottomSheet = showSettingsBottomSheet,
                onDismiss = { showSettingsBottomSheet = false },
                onInsertCurrencies = {
                    viewModel.onInsertCurrencies()
                },
                onClearCurrencies = {
                    viewModel.onClearCurrencies()
                }
            )
        }
    }

@Composable
private fun CurrencyScreenContent(
    currencies: ImmutableList<CurrencyUiModel>,
    currencyType: CurrencyTypeUiModel,
    isSearching: Boolean,
    onSearchClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onSearchCancel: () -> Unit,
    onSearching: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        AnimatedVisibility(!isSearching) {
            Header(
                modifier = Modifier
                    .padding(horizontal = CurrencyTheme.dimensions.spacingMedium)
                    .height(60.dp),
                currencyType = currencyType,
                onFilterClick = onFilterClick,
                onSearchClick = onSearchClick,
                onSettingsClick = onSettingsClick
            )
        }
        AnimatedVisibility(isSearching) {
            SearchBar(
                modifier = Modifier
                    .padding(horizontal = CurrencyTheme.dimensions.spacingMedium)
                    .height(60.dp),
                onBackAction = onSearchCancel,
                onValueChange = onSearching
            )
        }
        LazyColumn(
            state = rememberLazyListState()
        ) {
            itemsIndexed(currencies) { _, currency ->
                CurrencyItem(currency)
            }
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
                contentDescription = stringResource(R.string.right_arrow_content_description)
            )
        }
    }
    HorizontalDivider(color = MaterialTheme.colorScheme.tertiary)
}
