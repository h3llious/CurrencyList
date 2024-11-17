package com.nhatbui.currency.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nhatbui.common.ui.component.Screen
import com.nhatbui.common.ui.theme.CurrencyTheme
import com.nhatbui.currency.presentation.CurrencyViewModel
import com.nhatbui.currency.presentation.model.CurrencyPresentationState
import com.nhatbui.currency.ui.composable.CurrencyItem
import com.nhatbui.currency.ui.composable.FilterBottomSheet
import com.nhatbui.currency.ui.composable.Header
import com.nhatbui.currency.ui.composable.SettingsBottomSheet
import com.nhatbui.currency.ui.di.CurrencyDependencies
import com.nhatbui.currency.ui.model.CurrencyTypeUiModel
import com.nhatbui.currency.ui.model.CurrencyUiModel
import com.nhatbui.currency.ui.model.toPresentation
import com.nhatbui.currency.ui.model.toUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

private const val QUERY_DEBOUNCE_TIME = 200L

@OptIn(FlowPreview::class)
@Composable
internal fun CurrencyDependencies.CurrencyScreen() =
    Screen<CurrencyPresentationState, CurrencyViewModel> {
        var showFilterBottomSheet by remember { mutableStateOf(false) }
        var showSettingsBottomSheet by remember { mutableStateOf(false) }
        var showSearchBar by remember { mutableStateOf(false) }
        var searchText by remember { mutableStateOf("") }
        Content { viewState ->
            LaunchedEffect(Unit) {
                viewModel.getCurrencies(viewState.currencyType, searchText)
            }
            LaunchedEffect(searchText) {
                snapshotFlow { searchText }
                    .debounce(QUERY_DEBOUNCE_TIME)
                    .collectLatest { query ->
                        viewModel.getCurrencies(viewState.currencyType, query)
                    }
            }
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
                    viewModel.getCurrencies(type.toPresentation(), searchText)
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
        Header(
            modifier = Modifier
                .padding(horizontal = CurrencyTheme.dimensions.spacingMedium)
                .height(60.dp),
            currencyType = currencyType,
            isSearching = isSearching,
            onFilterClick = onFilterClick,
            onSearchClick = onSearchClick,
            onSettingsClick = onSettingsClick,
            onSearchCancel = onSearchCancel,
            onSearching = onSearching
        )
        LazyColumn(
            state = rememberLazyListState()
        ) {
            itemsIndexed(currencies) { _, currency ->
                CurrencyItem(currency)
            }
        }
    }
}
